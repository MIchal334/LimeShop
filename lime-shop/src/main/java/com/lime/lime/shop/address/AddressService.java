package com.lime.lime.shop.address;

import com.lime.lime.shop.address.modelForRestClient.Items;
import com.lime.lime.shop.address.modelForRestClient.Position;
import com.lime.lime.shop.address.modelForRestClient.ResponseModel;
import com.lime.lime.shop.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class AddressService {
    private final String apiKye = "r3BeIqGW9fRhVQbhPPuv8MWVISYOD47OBtOLZf3hKuE";

    public Position getLatAndLon(UserDTO userData){
        RestTemplate restClient  = new RestTemplate();
        ResponseModel response = new ResponseModel();
        if(userData.getStreet().equals("-")){
         userData.setStreet("");
        }
        String address = userData.getHouseNumber() + " " + userData.getStreet() + "," + userData.getCity()+","+userData.getPostCode();
        String url = "https://geocode.search.hereapi.com/v1/geocode?apiKey="+apiKye+"&q="+address;

        response = restClient.getForObject(url,ResponseModel.class);

        Items items = Arrays.stream(response.getItems()).findFirst().orElseThrow(() -> new IllegalStateException("This addrees mot exist"));

        return items.getPosition();
    }

}
