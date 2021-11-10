package com.lime.lime.shop.user;

import com.lime.lime.shop.address.AddressEntity;
import com.lime.lime.shop.address.AddressService;
import com.lime.lime.shop.address.modelForRestClient.Position;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleService;
import com.lime.lime.shop.keycloak.KeycloakService;
import com.lime.lime.shop.validators.UserDataValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final UserDataValidator userDataValidator;
    private final KeycloakService keycloakService;

    public UserService(RoleService roleService, UserRepository userRepository, AddressService addressService, UserDataValidator userDataValidator, KeycloakService keycloakService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.userDataValidator = userDataValidator;
        this.keycloakService = keycloakService;
    }


    public UserEntity handleCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        String keycloakId = auth.getName();
        String username = keycloakService.findUserNameByUserId(keycloakId);
        UserEntity currentUser = findUserByUserName(username);
        return currentUser;
    }


    public UserEntity creteNewAccount(UserDTO newUser) {

        newUser.setRoleName(newUser.getRoleName().toUpperCase());
        userDataValidator.validData(newUser, Optional.empty());

        UserEntity userToAdd = prepareUserToCreate(newUser);

        keycloakService.creteNewUser(newUser);
        keycloakService.addRoleToUser(newUser);
        UserEntity result = userRepository.save(userToAdd);
        return result;
    }


    public UserEntity editProfile(UserDTO newUserData) {
        UserEntity currentUser = handleCurrentUser();
        String oldUsername = handleCurrentUser().getUsername();
        userDataValidator.validData(newUserData, Optional.of(currentUser));
        UserEntity userToUpdate = prepareUserToUpdate(currentUser, newUserData);

        keycloakService.updateUser(newUserData,oldUsername);
        keycloakService.addRoleToUser(newUserData);
        UserEntity result = userRepository.save(userToUpdate);
        return result;

    }

    private UserEntity findUserByUserName(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new IllegalStateException("User not exist"));
    }

    private UserEntity prepareUserToCreate(UserDTO newUser) {
        Position position = addressService.getLatAndLon(newUser);
        RoleEntity role = roleService.getRoleByName(newUser.getRoleName());
        AddressEntity address = new AddressEntity(newUser, position);
        UserEntity userToAdd = new UserEntity(newUser, address, role);
        return userToAdd;
    }

    private UserEntity prepareUserToUpdate(UserEntity currentUser, UserDTO newData) {
        AddressEntity address = currentUser.getAddress();
        Position position = addressService.getLatAndLon(newData);
        address.setPosition(position);
        address.setCity(newData.getCity());
        address.setStreet(newData.getStreet());
        address.setHouseNumber(newData.getHouseNumber());
        currentUser.setName(newData.getName());
        currentUser.setUsername(newData.getUsername());
        currentUser.setLastName(newData.getLastName());
        currentUser.setPhoneNumber(newData.getPhoneNumber());
        currentUser.setEmail(newData.getEmail());
        currentUser.setAddress(address);
        return currentUser;
    }
}
