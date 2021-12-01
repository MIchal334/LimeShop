package com.lime.lime.shop.service;

import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.repository.LimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LimeServiceTest {

    @Test
    @DisplayName("should throw ResourceNotExistsException because lime not exist")
    void getLimeById_with_not_exist_lime() {
        var mockLimeRepository = mock(LimeRepository.class);

        when(mockLimeRepository.getLimeById(any())).thenReturn(Optional.empty());

        var toTest = new LimeService(mockLimeRepository,null);

        var exception = assertThatExceptionOfType(ResourceNotExistsException.class)
                .isThrownBy(() -> toTest.getLimeById(anyLong()));
        exception.withMessageContaining("lime not exist");
    }


    private LimeEntity mockLimeEntity(Long id){
        LimeEntity mockLimeEntity = new LimeEntity();
        mockLimeEntity.setId(id);
        mockLimeEntity.setAmount(100);
        return mockLimeEntity;
    }
}