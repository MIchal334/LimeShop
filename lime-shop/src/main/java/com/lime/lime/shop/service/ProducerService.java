package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.ClientPreducentRelation;
import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import com.lime.lime.shop.repository.OrderRepository;
import com.lime.lime.shop.validators.LimeDataValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final LimeRepository limeRepository;
    private final LimeDataValidator limeDataValidator;

    public ProducerService(OrderRepository orderRepository, UserService userService, LimeRepository limeRepository, LimeDataValidator limeDataValidator) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.limeRepository = limeRepository;
        this.limeDataValidator = limeDataValidator;
    }

    public List<ProducerOrderReadModel> getActuallyOrders() {
        UserEntity user = userService.handleCurrentUser();

        List<ProducerOrderReadModel> orderList = orderRepository.getActuallyOrdersByProducerId(user.getId())
                .stream()
                .filter(x -> !x.isDeleted() && x.isAccept() && !x.isCheck())
                .map(x -> new ProducerOrderReadModel(x, x.getClient().getAddress()))
                .collect(Collectors.toList());

        return orderList;
    }

    public List<LimeDTO> getAllLimeOfProducer() {
        UserEntity user = userService.handleCurrentUser();
        List<LimeDTO> listOfLime = limeRepository.getAllLimeByProducerId(user.getId())
                .stream()
                .filter(x -> !x.isDeleted())
                .map(LimeDTO::new)
                .collect(Collectors.toList());
        return listOfLime;
    }

    public void addNewLime(LimeDTO newLime) {
        UserEntity user = userService.handleCurrentUser();
        limeDataValidator.validData(newLime,user,false);
        LimeEntity limeToSave = new LimeEntity(newLime,user);
        limeRepository.save(limeToSave);

    }

    public void updateAmountOfLime(Long id, Integer newResource) {
        UserEntity user = userService.handleCurrentUser();
        LimeEntity lime = getLimeById(id);
        int oldAmount = lime.getAmount();
        lime.setAmount(newResource);

        limeDataValidator.validData(new LimeDTO(lime),user,true);
        lime.setAmount(oldAmount+ newResource);
        limeRepository.save(lime);
    }

    @Transactional
    public void deleteOfLime(Long id) {
        UserEntity user = userService.handleCurrentUser();
        LimeEntity lime = getLimeById(id);
        limeDataValidator.ownerValidation(lime.getId(),user.getId());
        lime.setDeleted(true);
    }


    public List<UserDTO> getAllClients() {
        UserEntity user = userService.handleCurrentUser();
        return user.getMyClient()
                .stream()
                .map(ClientPreducentRelation::getClient)
                .filter(o -> !o.isDeleted())
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }



    private LimeEntity getLimeById(Long id){

        return limeRepository.getLimeById(id)
                .orElseThrow(() -> new ResourceNotExistsException("This lime not exist"));
    }

}
