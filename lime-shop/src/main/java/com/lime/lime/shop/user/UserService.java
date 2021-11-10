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
        userDataValidator.validData(newUser);

        Position position = addressService.getLatAndLon(newUser);
        RoleEntity role = roleService.getRoleByName(newUser.getRoleName());
        AddressEntity address = new AddressEntity(newUser, position);
        UserEntity userToAdd = new UserEntity(newUser, address, role);

        keycloakService.creteNewUser(newUser);
        keycloakService.addRoleToUser(newUser);
        userRepository.save(userToAdd);
        return userToAdd;
    }

    private UserEntity findUserByUserName(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new IllegalStateException("User not exist"));
    }
}
