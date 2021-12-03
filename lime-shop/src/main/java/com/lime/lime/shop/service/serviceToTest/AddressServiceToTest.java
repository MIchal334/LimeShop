package com.lime.lime.shop.service.serviceToTest;

import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.modelForRestClient.Position;
import com.lime.lime.shop.repository.AddressRepository;
import com.lime.lime.shop.service.serviceInterface.AddressServiceInterface;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class AddressServiceToTest implements AddressServiceInterface {

    private final AddressRepository addressRepository;

    public AddressServiceToTest(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Position getLatAndLon(UserDTO userData) {
        return new Position();
    }

    public void deleteAddressByUserId(Long id) {
        AddressEntity addressToDelete = addressRepository.getAddressByUserId(id);
        addressToDelete.setDeleted(true);
    }


}
