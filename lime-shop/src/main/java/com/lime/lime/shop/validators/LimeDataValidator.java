package com.lime.lime.shop.validators;

import com.lime.lime.shop.exceptionHandler.exception.ResourceAlreadyExistsException;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LimeDataValidator {

    private final LimeRepository limeRepository;

    public LimeDataValidator(LimeRepository limeRepository) {
        this.limeRepository = limeRepository;
    }

    public void validNewLimeData(LimeDTO newLime, UserEntity user, Boolean isUpdate) {

        amountValidation(newLime.getAmount());
        priceValidation(newLime.getPrice());

        if(isUpdate){
            ownerValidation(newLime.getId(),user.getId());

        }else {
            typeValidation(newLime.getType(), user.getId());
        }


    }

    public void validNewOrder(LimeEntity lime, Long amountInOrder) {
        amountValidation(amountInOrder);
        checkIfTooMany(lime.getAmount(),amountInOrder);
    }


    public void ownerValidation(Long limeId, Long userId) {
       List<LimeEntity> limesOfUser = limeRepository.getAllLimeByProducerId(userId);

       if(!limesOfUser.stream().map(LimeEntity::getId).collect(Collectors.toList()).contains(limeId)){
           throw new IllegalStateException("You are not a owner this lime");
       }
    }



    private void typeValidation(String type, Long id) {
        Optional<LimeEntity> optionalOfLime = limeRepository.getLimeByTypeAndProducerId(type, id);

        if (optionalOfLime.isPresent()){
            throw  new ResourceAlreadyExistsException("You already have added this lime");
        }

    }

    private void amountValidation(long amount) {
        if (amount <= 0) {
            throw new IllegalStateException("The amount should be more then 0");
        }
    }

    private void priceValidation(long price){
        if (price <= 0) {
            throw new IllegalStateException("The price should be more then 0");
        }
    }

    private void checkIfTooMany(long amountOfLime, long amountInOrder){
        if(amountInOrder > amountOfLime){
            throw  new IllegalStateException("Not enough lime");
        }
    }


}
