package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.UserEntity;

public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String city;

    private String street;

    private String houseNumber;

    private String postCode;

    private String roleName;

    public UserDTO() {
    }

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.city = user.getAddress().getCity();
        this.street = user.getAddress().getStreet();
        this.houseNumber = user.getAddress().getHouseNumber();
        this.roleName = user.getRole().getRoleName();
        this.postCode = user.getAddress().getPostCode();
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getEmail() {
        return email;
    }


    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPassword() {
        return password;
    }

    public String getPostCode() {
        return postCode;
    }
}
