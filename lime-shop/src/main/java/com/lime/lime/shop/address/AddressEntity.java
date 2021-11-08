package com.lime.lime.shop.address;

import com.lime.lime.shop.address.modelForRestClient.Position;
import com.lime.lime.shop.user.UserDTO;
import com.lime.lime.shop.user.UserEntity;

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
}
