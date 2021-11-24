package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;

import java.time.LocalDateTime;

public class OrderReadModel {

    private Long orderId;
    private AddressDTO userAddress;
    private String userName;
    private String userLastName;
    private String userPhoneNumber;
    private String userEmail;
    private String limeType;
    private Long amount;
    private LocalDateTime dateOfReceipt;
    private String status;

    public OrderReadModel() {
    }

    public OrderReadModel(UserEntity user, LimeEntity lime, OrderEntity order) {
        this.orderId = order.getId();
        this.userAddress = new AddressDTO(user.getAddress());
        this.userName = user.getName();
        this.userLastName = user.getLastName();
        this.userPhoneNumber = user.getPhoneNumber();
        this.userEmail = user.getEmail();
        this.limeType = lime.getType();
        this.amount = order.getAmount();
        this.dateOfReceipt = order.getDateOfReceipt();
        this.status = order.getStatus().getStatusName();
    }

    public Long getOrderId() {
        return orderId;
    }

    public AddressDTO getUserAddress() {
        return userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getLimeType() {
        return limeType;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getDateOfReceipt() {
        return dateOfReceipt;
    }

    public String getStatus() {
        return status;
    }
}
