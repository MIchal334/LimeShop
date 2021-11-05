package com.lime.lime.shop.user;

import com.lime.lime.shop.address.AddressEntity;
import com.lime.lime.shop.lime.LimeEntity;
import com.lime.lime.shop.order.OrderEntity;
import com.lime.lime.shop.user.dictionaryTable.ClientPreducentRelation;
import com.lime.lime.shop.user.dictionaryTable.RoleEntity;

import javax.persistence.*;
import java.util.Set;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "role")
    RoleEntity role;

    @Column(name = "isDeleted")
    Boolean isDeleted;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    Set<LimeEntity> limes;

    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    Set<OrderEntity> producerOrders;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    Set<OrderEntity> clientOrders;


    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    Set<ClientPreducentRelation> myDealer;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    Set<ClientPreducentRelation> myClient;



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

    public AddressEntity getAddress() {
        return address;
    }

    public RoleEntity getRole() {
        return role;
    }

    public Set<LimeEntity> getLimes() {
        return limes;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }
}
