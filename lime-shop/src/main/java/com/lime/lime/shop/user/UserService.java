package com.lime.lime.shop.user;

import com.lime.lime.shop.address.AddressEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserService(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    public UserEntity creteNewAccount(UserDTO newUser) {
        RoleEntity role = roleService.getRoleByName(newUser.getRoleName());
        AddressEntity address = new AddressEntity(newUser);
        UserEntity userToAdd = new UserEntity(newUser,address,role);
        userRepository.save(userToAdd);
        return userToAdd;
    }
}
