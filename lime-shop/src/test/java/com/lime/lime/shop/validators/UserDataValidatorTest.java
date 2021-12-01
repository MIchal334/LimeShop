package com.lime.lime.shop.validators;

import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleRepository;
import com.lime.lime.shop.exceptionHandler.exception.ResourceAlreadyExistsException;

import com.lime.lime.shop.exceptionHandler.exception.WrongFormatException;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class UserDataValidatorTest {


    @Test
    @DisplayName("should return ResourceAlreadyExistsException because username exist in DB")
    void validData_with_exist_Username_During_Create_User() {
        var userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123", "email", "role"), Optional.empty()));
        exception.withMessageContaining("username exist");
    }


    @Test
    @DisplayName("should return ResourceAlreadyExistsException because phone number exist in DB")
    void validData_with_exist_phone_number_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.getUserByPhoneNumber(anyString())).thenReturn(Optional.of(new UserEntity()));

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123", "email", "role"), Optional.empty()));
        exception.withMessageContaining("phone number exist");

    }


    @Test
    @DisplayName("should return WrongFormatException because phone number is to short")
    void validData_with_too_short_phone_number_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.getUserByPhoneNumber(anyString())).thenReturn(Optional.empty());

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(WrongFormatException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("1234", anyString(), "role"), Optional.empty()));
        exception.withMessageContaining("has wrong format");

    }


    @Test
    @DisplayName("should return WrongFormatException because phone number is to long")
    void validData_with_too_long_phone_number_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.getUserByPhoneNumber(anyString())).thenReturn(Optional.empty());

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(WrongFormatException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("1234678900", anyString(), "role"), Optional.empty()));
        exception.withMessageContaining("has wrong format");

    }


    @Test
    @DisplayName("should return ResourceAlreadyExistsException because email exist in DB")
    void validData_with_email_exits_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.getUserByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", anyString(), "role"), Optional.empty()));
        exception.withMessageContaining("email exist");

    }


    @Test
    @DisplayName("should return WrongFormatException because email has wrong format")
    void validData_with_wrong_email_format_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.getUserByEmail(anyString())).thenReturn(Optional.empty());

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(WrongFormatException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", "email", "role"), Optional.empty()));
        exception.withMessageContaining("email has wrong format");

    }


    @Test
    @DisplayName("should return IllegalArgumentException because role not exist")
    void validData_with_wrong_role_name_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        var roleRepositoryMock = mock(RoleRepository.class);

        when(roleRepositoryMock.findRoleNameWithoutAdmin()).thenReturn(List.of("PRODUCER", "CLIENT"));


        var toTest = new UserDataValidator(userRepositoryMock, roleRepositoryMock);

        var exception = assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", "email@wp.pl", "role"), Optional.empty()));
        exception.withMessageContaining("role is not available");

    }


    @Test
    @DisplayName("should return IllegalArgumentException because ADMIN role is not available")
    void validData_with_role_name_as_ADMIN_during_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        var roleRepositoryMock = mock(RoleRepository.class);

        when(roleRepositoryMock.findRoleNameWithoutAdmin()).thenReturn(List.of("PRODUCER", "CLIENT"));


        var toTest = new UserDataValidator(userRepositoryMock, roleRepositoryMock);

        var exception = assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", "email@wp.pl", "ADMIN"), Optional.empty()));
        exception.withMessageContaining("role is not available");

    }


    @Test
    @DisplayName("should return ResourceAlreadyExistsException because username exist in DB")
    void validData_with_exist_Username_During_Update_User() {
        var userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123", "email", "role"),
                        Optional.of(mockUserEntity("321", "mail", "role", "name"))));
        exception.withMessageContaining("username exist");
    }


    @Test
    @DisplayName("should return ResourceAlreadyExistsException because phone number exist in DB")
    void validData_with_exist_phone_number_during_update_user() {
        var userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(userRepositoryMock.getUserByPhoneNumber(anyString())).thenReturn(Optional.of(new UserEntity()));

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123", "email", "role"),
                        Optional.of(mockUserEntity("321", "mail", "role", "userName"))));
        exception.withMessageContaining("phone number exist");

    }


    @Test
    @DisplayName("should return WrongFormatException because phone number is to short")
    void validData_with_too_short_phone_number_during_update_user() {
        var userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(userRepositoryMock.getUserByPhoneNumber(anyString())).thenReturn(Optional.empty());

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(WrongFormatException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("1234", anyString(), "role"),
                        Optional.of(mockUserEntity("321", "mail", "role", "userName"))));
        exception.withMessageContaining("has wrong format");

    }


    @Test
    @DisplayName("should return WrongFormatException because phone number is to short")
    void validData_with_too_long_phone_number_during_update_user() {
        var userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(userRepositoryMock.getUserByPhoneNumber(anyString())).thenReturn(Optional.empty());

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(WrongFormatException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("12345678900", anyString(), "role"),
                        Optional.of(mockUserEntity("321", "mail", "role", "userName"))));
        exception.withMessageContaining("has wrong format");

    }


    @Test
    @DisplayName("should return ResourceAlreadyExistsException because email exist in DB")
    void validData_with_email_exits_during_update_user() {
        var userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(userRepositoryMock.getUserByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", anyString(), "role"),
                        Optional.of(mockUserEntity("123456789", "mail", "role", "userName"))));
        exception.withMessageContaining("email exist");

    }


    @Test
    @DisplayName("should return WrongFormatException because email has wrong format")
    void validData_with_wrong_email_format_during_update_user() {
        var userRepositoryMock = mock(UserRepository.class);

        when(userRepositoryMock.getUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        when(userRepositoryMock.getUserByEmail(anyString())).thenReturn(Optional.empty());

        var toTest = new UserDataValidator(userRepositoryMock, null);

        var exception = assertThatExceptionOfType(WrongFormatException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", "email", "role"),
                        Optional.of(mockUserEntity("123456789", "mail", "role", "userName"))));
        exception.withMessageContaining("email has wrong format");

    }


    @Test
    @DisplayName("should return IllegalArgumentException because role not exist")
    void validData_with_wrong_role_name_during_Update_user() {
        var userRepositoryMock = mock(UserRepository.class);
        var roleRepositoryMock = mock(RoleRepository.class);

        when(roleRepositoryMock.findRoleNameWithoutAdmin()).thenReturn(List.of("PRODUCER", "CLIENT"));


        var toTest = new UserDataValidator(userRepositoryMock, roleRepositoryMock);

        var exception = assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", "email@wp.pl", "role"),
                        Optional.of(mockUserEntity("123456789", "email@wp.pl", "role", "userName"))));
        exception.withMessageContaining("role is not available");
    }

    @Test
    @DisplayName("should return IllegalArgumentException because ADMIN role is not available")
    void validData_with_role_name_as_ADMIN_during_update_user() {
        var userRepositoryMock = mock(UserRepository.class);
        var roleRepositoryMock = mock(RoleRepository.class);

        when(roleRepositoryMock.findRoleNameWithoutAdmin()).thenReturn(List.of("PRODUCER", "CLIENT"));


        var toTest = new UserDataValidator(userRepositoryMock, roleRepositoryMock);

        var exception = assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> toTest.validData(mockUserDTO("123456789", "email@wp.pl", "ADMIN"),
                        Optional.of(mockUserEntity("123456789", "email@wp.pl", "role", "userName"))));
        exception.withMessageContaining("role is not available");

    }


    @Test
    @DisplayName("should dose not throw any exception ")
    void validData_everything_is_ok_for_update_user() {
        var userRepositoryMock = mock(UserRepository.class);
        var roleRepositoryMock = mock(RoleRepository.class);

        when(roleRepositoryMock.findRoleNameWithoutAdmin()).thenReturn(List.of("PRODUCER", "CLIENT"));


        var toTest = new UserDataValidator(userRepositoryMock, roleRepositoryMock);

        assertDoesNotThrow(() -> toTest.validData(mockUserDTO("123456789", "email@wp.pl", "PRODUCER"),
                Optional.of(mockUserEntity("123456789", "email@wp.pl", "CLIENT", "userName"))));

    }



    @Test
    @DisplayName("should dose not throw any exception ")
    void validData_everything_is_ok_for_create_user() {
        var userRepositoryMock = mock(UserRepository.class);
        var roleRepositoryMock = mock(RoleRepository.class);

        when(roleRepositoryMock.findRoleNameWithoutAdmin()).thenReturn(List.of("PRODUCER", "CLIENT"));


        var toTest = new UserDataValidator(userRepositoryMock, roleRepositoryMock);


        assertDoesNotThrow(() -> toTest.validData(mockUserDTO("123456789", "email@wp.pl", "PRODUCER"),
                Optional.empty()));


    }

    public UserDTO mockUserDTO(String phoneNumber, String email, String roleName) {
        UserDTO mockUser = new UserDTO();
        mockUser.setName("name");
        mockUser.setUsername("userName");
        mockUser.setPhoneNumber(phoneNumber);
        mockUser.setEmail(email);
        mockUser.setRoleName(roleName);
        return mockUser;
    }

    public UserEntity mockUserEntity(String phoneNumber, String email, String roleName, String userName) {
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(userName);
        mockUser.setEmail(email);
        mockUser.setPhoneNumber(phoneNumber);
        mockUser.setRole(mockRoleEntity(roleName));
        return mockUser;
    }

    public RoleEntity mockRoleEntity(String roleName) {
        RoleEntity mockRole = new RoleEntity();
        mockRole.setRoleName(roleName);
        return mockRole;
    }

}