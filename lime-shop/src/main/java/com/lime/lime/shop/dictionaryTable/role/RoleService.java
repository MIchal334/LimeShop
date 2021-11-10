package com.lime.lime.shop.dictionaryTable.role;

import com.lime.lime.shop.keycloak.KeycloakConfig;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<RoleRepresentation> getAllRoleToRoleRepresentation() {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();

        for (RoleEntity role : roleRepository.findAll()) {
            roleRepresentations.add(KeycloakConfig
                    .getInstance()
                    .realm(KeycloakConfig.realm)
                    .roles()
                    .get(role.getRoleName())
                    .toRepresentation());
        }

        return roleRepresentations;
    }

}
