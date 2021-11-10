package com.lime.lime.shop.keycloak;

import com.lime.lime.shop.user.UserDTO;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class KeycloakService {

    public void creteNewUser(UserDTO userData){
        UserRepresentation newUser = new UserRepresentation();
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(userData.getPassword());

        newUser.setUsername(userData.getUsername());
        newUser.setCredentials(Collections.singletonList(credentialRepresentation));
        newUser.setEmail(userData.getEmail());
        newUser.setEnabled(true);
        newUser.setEmailVerified(false);

        usersResource.create(newUser).close();
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public void addRoleToUser(UserDTO user) {
        List<RoleRepresentation> role = preparingRoleRepresentationForUser(user);
        String userId = findUserIdByUserName(user.getUsername());

        UserResource usersResourceToAddRole = getUserResource(userId);
        usersResourceToAddRole.roles().realmLevel().add(role);
    }

    private List<RoleRepresentation> preparingRoleRepresentationForUser(UserDTO user) {
        List<RoleRepresentation> role = new ArrayList<>();

        role.add(
                KeycloakConfig
                        .getInstance()
                        .realm(KeycloakConfig.realm)
                        .roles()
                        .get(user.getRoleName())
                        .toRepresentation()
        );

        return role;
    }

    private UserResource getUserResource(String userId) {
        UserResource usersResourceToAddRole = KeycloakConfig
                .getInstance()
                .realm(KeycloakConfig.realm)
                .users()
                .get(userId);
        return usersResourceToAddRole;
    }

    private String findUserIdByUserName(String user) {
        return KeycloakConfig
                .getInstance()
                .realm(KeycloakConfig.realm)
                .users()
                .search(user)
                .get(0)
                .getId();
    }

}
