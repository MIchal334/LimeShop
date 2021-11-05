package com.lime.lime.shop.address;

import com.lime.lime.shop.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "city")
    String city;

    @Column(name = "street")
    String street;

    @Column(name = "house_number")
    String houseNumber;

    @Column(name = "lon")
    Float lon;

    @Column(name = "lat")
    Float lat;

    @OneToOne(mappedBy = "address")
    private UserEntity user;

    public AddressEntity() {
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
