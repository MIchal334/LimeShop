package com.lime.lime.shop.address;

import com.lime.lime.shop.address.modelForRestClient.Position;
import com.lime.lime.shop.address.modelForRestClient.ResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {
    private final String apiKye = "yv7ZVvc1awCOEcRn-OQLHyIfHv1XG6KeaGSxCOcKNYQ";

    public Position getLatAndLon(String city, String street, String houseNumber, String postCode){
        RestTemplate restClient  = new RestTemplate();
        ResponseModel response = new ResponseModel();
        if(street.equals("-")){
         street = "";
        }
        String address = houseNumber + " " + street + "," + city+","+postCode;
        String url = "https://geocode.search.hereapi.com/v1/geocode?apiKey="+apiKye+"&q="+address;

        response = restClient.getForObject(url,ResponseModel.class);

        return null;
    }

}
