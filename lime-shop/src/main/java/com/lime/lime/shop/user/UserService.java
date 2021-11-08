package com.lime.lime.shop.user;

import com.lime.lime.shop.address.AddressEntity;
import com.lime.lime.shop.address.AddressService;
import com.lime.lime.shop.address.modelForRestClient.Position;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AddressService addressService;

    public UserService(RoleService roleService, UserRepository userRepository, AddressService addressService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    public UserEntity creteNewAccount(UserDTO newUser) {
        Position position = addressService.getLatAndLon(newUser);
        RoleEntity role = roleService.getRoleByName(newUser.getRoleName());
        AddressEntity address = new AddressEntity(newUser,position);
        UserEntity userToAdd = new UserEntity(newUser,address,role);
        userRepository.save(userToAdd);
        return userToAdd;
    }
}
