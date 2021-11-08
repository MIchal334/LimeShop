package com.lime.lime.shop.validators;

import com.lime.lime.shop.user.UserDTO;
import com.lime.lime.shop.user.UserEntity;
import com.lime.lime.shop.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDataValidator {

    private final UserRepository userRepository;

    public UserDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validData(UserDTO newUser){
        userNameValidation(newUser.getUsername());
    }

    private void userNameValidation(String username){
        Optional<UserEntity> user  = userRepository.getUserByUsername(username);

        if(user.isPresent()){
            throw new IllegalStateException("User with this username exist");
        }

    }
}
