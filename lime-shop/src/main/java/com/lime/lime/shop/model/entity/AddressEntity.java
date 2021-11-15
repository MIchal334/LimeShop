package com.lime.lime.shop.model.entity;

import com.lime.lime.shop.model.modelForRestClient.Position;
import com.lime.lime.shop.model.dto.UserDTO;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne(mappedBy = "address")
    private UserEntity user;

    public AddressEntity() {
    }

    public AddressEntity(UserDTO newUser, Position position) {
        this.city = newUser.getCity();
        this.street = newUser.getStreet();
        this.houseNumber = newUser.getHouseNumber();
        this.lat = position.getLat();
        this.lon = position.getLng();
        this.postCode = newUser.getPostCode();
        this.isDeleted = false;

    }

    public Long getId() {
        return id;
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

    public Float getLon() {
        return lon;
    }

    public Float getLat() {
        return lat;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPosition( Position position){
        this.lon = position.getLng();
        this.lat = position.getLat();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
