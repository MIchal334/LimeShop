package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;

import javax.mail.Address;
import javax.persistence.*;
import java.time.LocalDateTime;

public class ProducerOrderReadModel {

    private Long id;

    private AddressDTO clientAddress;
    private String clientName;
    private String clientLastName;
    private String clientPhoneNumber;
    private String clientEmail;
    private String limeType;
    private Long amount;
    private LocalDateTime dateOfReceipt;
    private String status;

    public ProducerOrderReadModel() {
    }

    public ProducerOrderReadModel(OrderEntity order, AddressEntity clientAddress) {
        this.id = order.getId();
        this.clientAddress = new AddressDTO(clientAddress);
        this.clientName = order.getClient().getName();
        this.clientLastName = order.getClient().getLastName();
        this.clientPhoneNumber = order.getClient().getPhoneNumber();
        this.clientEmail = order.getClient().getEmail();
        this.limeType = order.getLime().getType();
        this.amount = order.getAmount();
        this.dateOfReceipt = order.getDateOfReceipt();
        this.status = order.getStatus().getStatusName();
    }
}
