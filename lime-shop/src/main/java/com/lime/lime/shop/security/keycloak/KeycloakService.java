package com.lime.lime.shop.security.keycloak;

import com.lime.lime.shop.dictionaryTable.role.RoleService;
import com.lime.lime.shop.model.dto.UserDTO;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class KeycloakService {

    private final RoleService roleService;

    public KeycloakService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void creteNewUser(UserDTO userData){
        UserRepresentation newUser = new UserRepresentation();
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakDataSource.realm).users();
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


    public String findUserNameByUserId(String id) {
        return KeycloakConfig
                .getInstance()
                .realm(KeycloakDataSource.realm)
                .users()
                .get(id)
                .toRepresentation()
                .getUsername();
    }


    public void updateUser(UserDTO updateUser, String username) {
        String userId = findUserIdByUserName(username);
        UserResource userResource = getUserResource(userId);

        UserRepresentation userToUpdate = userResource.toRepresentation();

        userToUpdate.setUsername(updateUser.getUsername());
        userToUpdate.setEmail(updateUser.getEmail());
        userToUpdate.getRealmRoles();

        userResource.roles().realmLevel().remove(roleService.getAllRoleToRoleRepresentation());
        userResource.update(userToUpdate);
    }

    public void setDisabled(String userName) {
        String userId = findUserIdByUserName(userName);
        UserResource userResource = getUserResource(userId);
        UserRepresentation userToUpdate = userResource.toRepresentation();
        userToUpdate.setEnabled(false);
        userResource.update(userToUpdate);
    }

    public void changePassword(String password, String login) {

        String userId = findUserIdByUserName(login);
        UserResource userResource = getUserResource(userId);
        UserRepresentation userToUpdate = userResource.toRepresentation();
        password = password.substring(1,password.length()-1);

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password.toString());
        userToUpdate.setCredentials(Collections.singletonList(credentialRepresentation));

        userResource.update(userToUpdate);
    }
    private List<RoleRepresentation> preparingRoleRepresentationForUser(UserDTO user) {
        List<RoleRepresentation> role = new ArrayList<>();

        role.add(
                KeycloakConfig
                        .getInstance()
                        .realm(KeycloakDataSource.realm)
                        .roles()
                        .get(user.getRoleName())
                        .toRepresentation()
        );

        return role;
    }


    private UserResource getUserResource(String userId) {
        UserResource usersResourceToAddRole = KeycloakConfig
                .getInstance()
                .realm(KeycloakDataSource.realm)
                .users()
                .get(userId);
        return usersResourceToAddRole;
    }

    private String findUserIdByUserName(String user) {
        return KeycloakConfig
                .getInstance()
                .realm(KeycloakDataSource.realm)
                .users()
                .search(user)
                .get(0)
                .getId();
    }

}
