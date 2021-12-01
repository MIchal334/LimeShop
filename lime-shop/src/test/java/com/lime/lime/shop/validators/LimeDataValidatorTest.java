package com.lime.lime.shop.validators;

import com.lime.lime.shop.exceptionHandler.exception.ResourceAlreadyExistsException;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LimeDataValidatorTest {

    @Test
    @DisplayName("should throw ResourceAlreadyExistsException this user already created this lime ")
    void validNewLimeData_with_with_lime_exist_during_crate_new() {
        var mockLimeRepository = mock(LimeRepository.class);

        when(mockLimeRepository.getLimeByTypeAndProducerId(anyString(), anyLong())).thenReturn(Optional.of(new LimeEntity()));

        var toTest = new LimeDataValidator(mockLimeRepository);

        var exception = assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> toTest.validNewLimeData(mockLimeDTO(1l, "a", 10), mockUserEntity(1l), false));
        exception.withMessageContaining("already have added");
    }



    @Test
    @DisplayName("should dose not throw any exception ")
    void validNewLimeData_everything_ok_during_crate_new() {
        var mockLimeRepository = mock(LimeRepository.class);

        when(mockLimeRepository.getLimeByTypeAndProducerId(anyString(), anyLong())).thenReturn(Optional.empty());

        var toTest = new LimeDataValidator(mockLimeRepository);

        assertDoesNotThrow(() -> toTest.validNewLimeData(
                mockLimeDTO(1l, "a", 10), mockUserEntity(1l), false));
    }

    @Test
    @DisplayName("should throw IllegalStateException because user is not owner of lime")
    void validNewLimeData_with_user_is_not_owner() {
        var mockLimeRepository = mock(LimeRepository.class);

        when(mockLimeRepository.getAllLimeByProducerId(anyLong())).thenReturn(List.of(mockLimeEntity(1l),mockLimeEntity(2l)));

        var toTest = new LimeDataValidator(mockLimeRepository);


        var exception = assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> toTest.validNewLimeData(mockLimeDTO(3l, "a", 10), mockUserEntity(1l), true));
        exception.withMessageContaining("not a owner this");
    }


    @Test
    @DisplayName("should dose not throw any exception ")
    void validNewLimeData_everything_ok_during_update() {
        var mockLimeRepository = mock(LimeRepository.class);

        when(mockLimeRepository.getAllLimeByProducerId(anyLong())).thenReturn(List.of(mockLimeEntity(1l),mockLimeEntity(2l)));

        var toTest = new LimeDataValidator(mockLimeRepository);



        assertDoesNotThrow(() -> toTest.validNewLimeData(
                mockLimeDTO(1l, "a", 10), mockUserEntity(1l), true));

    }


    @Test
    @DisplayName("should throw IllegalStateException because amount of lime less then 0 ")
    void validNewLimeData_with_amount_is_low_then_0() {

        var toTest = new LimeDataValidator(null);

        var exception = assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> toTest.validNewLimeData(mockLimeDTO(1l, "a", -12), mockUserEntity(1l), true));
    }


    @Test
    @DisplayName("should throw IllegalStateException because lime in Order is too much")
    void validNewOrder_with_to_much_lime_in_order(){
        var toTest = new LimeDataValidator(null);


        var exception = assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> toTest.validNewOrder(mockLimeEntity(1l),200l));
        exception.withMessageContaining("Not enough lime");

    }


    @Test
    @DisplayName("should dose not throw any exception ")
    void validNewOrder_with_ok_amount_lime_in_order(){
        var toTest = new LimeDataValidator(null);

        assertDoesNotThrow(()-> toTest.validNewOrder(mockLimeEntity(1l),50l));
    }



    private LimeEntity mockLimeEntity(Long id){
        LimeEntity mockLimeEntity = new LimeEntity();
        mockLimeEntity.setId(id);
        mockLimeEntity.setAmount(100);
        return mockLimeEntity;
    }

    private LimeDTO mockLimeDTO(Long id, String type, Integer amount) {
        LimeDTO mockLimeDTO = new LimeDTO();
        mockLimeDTO.setAmount(amount);
        mockLimeDTO.setType(type);
        mockLimeDTO.setId(id);
        return mockLimeDTO;
    }

    public UserEntity mockUserEntity(Long id) {
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(id);
        return mockUserEntity;
    }
}