package com.lime.lime.shop.model.entity;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.model.dto.UserDTO;
import org.jboss.resteasy.spi.touri.MappedBy;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "role")
    private RoleEntity role;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<LimeEntity> limes;

    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    private Set<OrderEntity> producerOrders;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<OrderEntity> clientOrders;



    public UserEntity() {
    }

    public UserEntity(UserDTO newUser, AddressEntity address, RoleEntity role) {
        this.username = newUser.getUsername();
        this.name = newUser.getName();
        this.lastName = newUser.getLastName();
        this.email = newUser.getEmail();
        this.phoneNumber = newUser.getPhoneNumber();
        this.address = address;
        this.role=role;
        this.isDeleted = false;

    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Set<OrderEntity> getProducerOrders() {
        return producerOrders;
    }

    public Set<OrderEntity> getClientOrders() {
        return clientOrders;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
