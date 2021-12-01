package com.lime.lime.shop.service;

import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AdministratorServiceTest {

    @Test
    @DisplayName("should throw ResourceNotExistsException because user not exist")
    void deleteUserByAdmin_with_not_exist_user() {
        var mockUserRepository = mock(UserRepository.class);

        when(mockUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        var toTest = new AdministratorService(mockUserRepository,null,null);

        var exception = assertThatExceptionOfType(ResourceNotExistsException.class)
                .isThrownBy(() -> toTest.deleteUserByAdmin(1l));
        exception.withMessageContaining("user not exist");
    }
}