package com.lime.lime.shop.validators;

import com.lime.lime.shop.exceptionHandler.exception.ResourceAlreadyExistsException;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LimeDataValidator {

    private final LimeRepository limeRepository;

    public LimeDataValidator(LimeRepository limeRepository) {
        this.limeRepository = limeRepository;
    }

    public void validData(LimeDTO newLime, UserEntity user) {
        amountValidation(newLime.getAmount());
        typeValidation(newLime.getType(), user.getId());

    }

    private void typeValidation(String type, Long id) {
        Optional<LimeEntity> optionalOfLime = limeRepository.getLimeByTypeAndProducerId(type, id);

        if (optionalOfLime.isPresent()){
            throw  new ResourceAlreadyExistsException("You already have created this lime");
        }

    }

    private void amountValidation(long amount) {
        if (amount <= 0) {
            throw new IllegalStateException("The amount should be more then 0");
        }
    }
}
