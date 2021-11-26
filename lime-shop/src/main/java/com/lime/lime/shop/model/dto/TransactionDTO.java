package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.OrderEntity;

import java.time.LocalDateTime;

public class TransactionDTO {
    private String clientFullName;
    private String dealerFullName;
    private AddressDTO clientAddress;
    private AddressDTO dealerAddress;
    private String clientPhoneNumber;
    private String dealerPhoneNumber;
    private String clientEmail;
    private String dealerEmail;
    private String limeType;
    private Integer amount;
    private LocalDateTime dateOfReceipt;
    private String status;

    public TransactionDTO(OrderEntity order) {
        this.clientFullName = order.getClient().getName() + " " + order.getClient().getLastName();
        this.dealerFullName = order.getProducer().getName() + " " + order.getProducer().getLastName();
        this.clientAddress = new AddressDTO(order.getClient().getAddress());
        this.dealerAddress = new AddressDTO(order.getProducer().getAddress());
        this.clientPhoneNumber = order.getClient().getPhoneNumber();
        this.dealerPhoneNumber = order.getProducer().getPhoneNumber();
        this.clientEmail = order.getClient().getEmail();
        this.dealerEmail = order.getProducer().getEmail();
        this.limeType = order.getLime().getType();
        this.amount = order.getLime().getAmount();
        this.dateOfReceipt = order.getDateOfReceipt();
        this.status = order.getStatus().getStatusName();
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public String getDealerFullName() {
        return dealerFullName;
    }

    public AddressDTO getClientAddress() {
        return clientAddress;
    }

    public AddressDTO getDealerAddress() {
        return dealerAddress;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getDealerPhoneNumber() {
        return dealerPhoneNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getDealerEmail() {
        return dealerEmail;
    }

    public String getLimeType() {
        return limeType;
    }

    public Integer getAmount() {
        return amount;
    }

    public LocalDateTime getDateOfReceipt() {
        return dateOfReceipt;
    }

    public String getStatus() {
        return status;
    }
}
