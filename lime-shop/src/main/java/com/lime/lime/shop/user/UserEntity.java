package com.lime.lime.shop.user;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username")
    String username;

    @Column(name = "name")
    String name;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    Long address;

    @Column(name = "role")
    Short role;

    @Column(name = "isDeleted")
    Boolean isDeleted;

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
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

    public Long getAddress() {
        return address;
    }

    public Short getRole() {
        return role;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }
}
