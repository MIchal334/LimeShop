package com.lime.lime.shop.service.serviceInterface;

import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.modelForRestClient.Position;

public interface AddressServiceInterface {

    public Position getLatAndLon(UserDTO userData);

    public void deleteAddressByUserId(Long id);
}
