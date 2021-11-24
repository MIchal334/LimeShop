package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.model.modelForRestClient.Position;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleService;
import com.lime.lime.shop.repository.ClientProducerRepository;
import com.lime.lime.shop.repository.UserRepository;
import com.lime.lime.shop.security.keycloak.KeycloakService;
import com.lime.lime.shop.security.SecurityService;
import com.lime.lime.shop.validators.UserDataValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final UserDataValidator userDataValidator;
    private final KeycloakService keycloakService;
    private final SecurityService securityService;
    private final LimeService limeService;
    private final OrderService orderService;
    private final ClientProducerRepository clientProducerRepository;

    public UserService(RoleService roleService, UserRepository userRepository, AddressService addressService, UserDataValidator userDataValidator, KeycloakService keycloakService, SecurityService securityService, LimeService limeService, OrderService orderService, ClientProducerRepository clientProducerRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.userDataValidator = userDataValidator;
        this.keycloakService = keycloakService;
        this.securityService = securityService;
        this.limeService = limeService;
        this.orderService = orderService;
        this.clientProducerRepository = clientProducerRepository;
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

        keycloakService.updateUser(newUserData, oldUsername);
        keycloakService.addRoleToUser(newUserData);
        UserEntity result = userRepository.save(userToUpdate);
        return result;

    }

    public void changePassword(MultiValueMap body) {
        String newPassword = String.valueOf(body.get("newPassword"));
        body.set("username", handleCurrentUser().getUsername());
        securityService.getToken(body);
        keycloakService.changePassword(newPassword, handleCurrentUser().getUsername());
    }

    @Transactional
    public void deleteUser() {
        UserEntity currentUser = handleCurrentUser();
        addressService.deleteAddressByUserId(currentUser.getId());
        orderService.deleteAllByUerId(currentUser.getId());
        deleteClientProducerRelationByUserId(currentUser.getId());

        if (currentUser.getRole().getRoleName().equals(RoleType.PRODUCER.name())) {
            limeService.deleteAllByUserId(currentUser.getId());
        }

        keycloakService.setDisabled(currentUser.getUsername());
        currentUser.setDeleted(true);
    }


    public List<UserEntity> getAllUserByRoleName(String roleName) {
        return userRepository.getAllUserByRoleName(roleName);
    }

    public UserEntity getUserByIdAndRole(Long userId, RoleType roleType) {
        UserEntity user = userRepository.findById(userId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new IllegalStateException("user not exist"));

        if (!user.getRole().getRoleName().equals(roleType.name())) {
            throw new IllegalStateException("This user is not:" + roleType.name());
        }
        return user;
    }

    private UserEntity findUserByUserName(String username) {
        return userRepository.getUserByUsername(username)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new IllegalStateException("User not exist or is deleted"));
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

    private void deleteClientProducerRelationByUserId(Long id){
       List<ClientProducerRelation> allRelations = clientProducerRepository.getAllRelationByUserId(id);
       allRelations
               .stream()
               .filter(r -> !r.isDeleted())
               .forEach(r -> {
                   r.setDeleted(true);
               });
    }


}
