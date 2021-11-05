package com.lime.lime.shop.dictionaryTable.role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity getRoleByName(String roleName){
        return roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role with this name not Exist"));
    }

}
