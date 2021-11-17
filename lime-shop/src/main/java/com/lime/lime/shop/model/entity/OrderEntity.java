package com.lime.lime.shop.model.entity;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producent_id")
    private UserEntity producer;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity client;

    @ManyToOne
    @JoinColumn(name = "lime_id")
    private LimeEntity lime;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "date_of_receipt")
    private LocalDateTime dateOfReceipt;

    @ManyToOne
    @JoinColumn(name = "status")
    private OrderStatusEntity status;



    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public UserEntity getProducer() {
        return producer;
    }

    public UserEntity getClient() {
        return client;
    }

    public LimeEntity getLime() {
        return lime;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getDateOfReceipt() {
        return dateOfReceipt;
    }

    public OrderStatusEntity getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEntity status) {
        this.status = status;
    }
}
