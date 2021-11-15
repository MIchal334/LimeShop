package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.UserEntity;

import javax.persistence.*;

public class AddressDTO {
    private String city;
    private String street;
    private String houseNumber;
    private String postCode;

    public AddressDTO() {
    }

    public AddressDTO(AddressEntity address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
        this.postCode = address.getPostCode();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostCode() {
        return postCode;
    }
}
