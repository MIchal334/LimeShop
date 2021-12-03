package com.lime.lime.shop;

import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class MockObjects {

    public UserDTO mockUserDTO(String phoneNumber, String email, String roleName,String username) {
        UserDTO mockUser = new UserDTO();
        mockUser.setName("name");
        mockUser.setUsername(username);
        mockUser.setPhoneNumber(phoneNumber);
        mockUser.setEmail(email);
        mockUser.setRoleName(roleName);
        return mockUser;
    }

    public UserEntity mockUserEntity(String phoneNumber, String email, String roleName, String userName) {
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(userName);
        mockUser.setEmail(email);
        mockUser.setName("Quebo");
        mockUser.setName("Aspartan");
        mockUser.setPhoneNumber(phoneNumber);
        mockUser.setRole(mockRoleEntity(roleName));
        mockUser.setDeleted(false);
        mockUser.setAddress(mockAddressEntity());
        return mockUser;
    }

    public RoleEntity mockRoleEntity(String roleName) {
        RoleEntity mockRole = new RoleEntity();
        mockRole.setRoleName(roleName);
        mockRole.setId((short)2);
        return mockRole;
    }

    public AddressEntity mockAddressEntity(){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Miasto");
        addressEntity.setDeleted(false);
        addressEntity.setHouseNumber("1");
        addressEntity.setPostCode("90-100");
        addressEntity.setStreet("ulica");
        return addressEntity;
    }


}

