package com.lime.lime.shop.service;

import com.lime.lime.shop.model.modelForRestClient.Items;
import com.lime.lime.shop.model.modelForRestClient.Position;
import com.lime.lime.shop.model.modelForRestClient.ResponseModel;
import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class AddressService {

    private final String apiKye = "6E-MqWwkVrVCCYnKuzrZI44lovTIyN-YBhm6rwdTqnU";
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void deleteAddressByUserId(Long id) {
        AddressEntity addressToDelete = addressRepository.getAddressByUserId(id);
        addressToDelete.setDeleted(true);
    }


    public Position getLatAndLon(UserDTO userData) {
        RestTemplate restClient = new RestTemplate();
        ResponseModel response = new ResponseModel();
        if (userData.getStreet().equals("-")) {
            userData.setStreet("");
        }
        String address = userData.getHouseNumber() + " " + userData.getStreet() + "," + userData.getCity() + "," + userData.getPostCode();
        String url = "https://geocode.search.hereapi.com/v1/geocode?apiKey=" + apiKye + "&q=" + address;

        response = restClient.getForObject(url, ResponseModel.class);

        Items items = Arrays.stream(response.getItems()).findFirst().orElseThrow(() -> new IllegalStateException("Address not found"));

        return items.getPosition();
    }


}
