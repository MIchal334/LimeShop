package com.lime.lime.shop.service;

import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import com.lime.lime.shop.validators.LimeDataValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LimeService {

    private final LimeRepository limeRepository;
    private final LimeDataValidator limeDataValidator;

    public LimeService(LimeRepository limeRepository, LimeDataValidator limeDataValidator) {
        this.limeRepository = limeRepository;
        this.limeDataValidator = limeDataValidator;
    }


    public List<LimeDTO> getAllLimeByProducerId(Long producerId){
        return limeRepository.getAllLimeByProducerId(producerId)
                .stream()
                .filter(x -> !x.isDeleted())
                .map(LimeDTO::new)
                .collect(Collectors.toList());
    }

    public LimeEntity getLimeById(Long id) {
        return limeRepository.getLimeById(id)
                .orElseThrow(() -> new ResourceNotExistsException("This lime not exist"));
    }

    public void saveNewLime(LimeDTO newLime, UserEntity user){
        limeDataValidator.validNewLimeData(newLime, user, false);
        LimeEntity limeToSave = new LimeEntity(newLime, user);
        limeRepository.save(limeToSave);
    }

    public void updateLime(Long limeId, Integer newResource,UserEntity user){
        LimeEntity lime = getLimeById(limeId);
        int oldAmount = lime.getAmount();
        lime.setAmount(newResource);

        limeDataValidator.validNewLimeData(new LimeDTO(lime), user, true);
        lime.setAmount(oldAmount + newResource);
        limeRepository.save(lime);
    }

    public void deleteLimeById(Long limeId,Long userId){
        LimeEntity lime = getLimeById(limeId);
        limeDataValidator.ownerValidation(lime.getId(), userId);
        lime.setDeleted(true);
    }

    public LimeEntity getNewOrder(Long limeId, Long amount) {
        LimeEntity lime = getLimeById(limeId);
        limeDataValidator.validNewOrder(lime,amount);
        lime.setAmount((int) (lime.getAmount() - amount));
        return limeRepository.save(lime);
    }

    public void deleteAllByUserId(Long producerId) {
        List<LimeEntity> allLimeOfProducer = limeRepository.getAllLimeByProducerId(producerId);

        allLimeOfProducer.forEach(o ->{
            o.setDeleted(true);
            limeRepository.save(o);
        });
    }
}
