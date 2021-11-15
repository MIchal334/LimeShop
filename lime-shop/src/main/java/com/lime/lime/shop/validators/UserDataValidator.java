package com.lime.lime.shop.validators;

import com.lime.lime.shop.dictionaryTable.role.RoleRepository;
import com.lime.lime.shop.exceptionHandler.exception.ResourceAlreadyExistsException;
import com.lime.lime.shop.exceptionHandler.exception.WrongFormatException;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDataValidator {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDataValidator(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void validData(UserDTO newUser,Optional<UserEntity> currentUser ) {
        Optional<UserEntity> previousUser = Optional.empty();
        if(currentUser.isPresent()){
         previousUser = currentUser;
         newUser.setRoleName(currentUser.get().getRole().getRoleName());
        }

        userNameValidation(newUser.getUsername(),previousUser);
        phoneNumberValidator(newUser.getPhoneNumber(),previousUser);
        emailFormatValidation(newUser.getEmail(),previousUser);
        roleValidator(newUser.getRoleName());

    }

    private void userNameValidation(String username, Optional<UserEntity> previousUser) {

        if(!previousUser.isPresent() || (previousUser.isPresent() && !previousUser.get().getUsername().equals(username)) ) {

            Optional<UserEntity> user = userRepository.getUserByUsername(username);

            if (user.isPresent()) {
                throw new ResourceAlreadyExistsException("User with this username exist");
            }
        }
    }

    private void phoneNumberValidator(String number, Optional<UserEntity> previousUser) {

        if(!previousUser.isPresent() || (previousUser.isPresent() && !previousUser.get().getPhoneNumber().equals(number)) ){
            Optional<UserEntity> user = userRepository.getUserByPhoneNumber(number);
            int size = number.toCharArray().length;

            if (user.isPresent()) {
                throw new ResourceAlreadyExistsException("User with this phone number exist");
            }

            if (size != 9) {
                throw new WrongFormatException("The phone number has wrong format");
            }
        }




    }

    private void emailFormatValidation(String email, Optional<UserEntity> previousUser) {
        if(!previousUser.isPresent() || (previousUser.isPresent() && !previousUser.get().getEmail().equals(email)) ){
            Optional<UserEntity> user = userRepository.getUserByEmail(email);
            int indexAt = email.indexOf('@');

            if (user.isPresent()) {
                throw new ResourceAlreadyExistsException("User with this email exist");
            }

            if (indexAt <= 0){
                throw new WrongFormatException("The email has wrong format");
            }
        }

    }

    private void roleValidator(String roleName){

        List<String> roleList = roleRepository.findRoleNameWithoutAdmin();
        if(!roleList.contains(roleName)){
            throw new IllegalArgumentException("This role is not available");
        }
    }

}
