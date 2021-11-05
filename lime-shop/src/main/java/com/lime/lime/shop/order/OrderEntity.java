package com.lime.lime.shop.order;

import com.lime.lime.shop.lime.LimeEntity;
import com.lime.lime.shop.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "producent_id")
    UserEntity producer;


    @ManyToOne
    @JoinColumn(name = "client_id")
    UserEntity client;

    @ManyToOne
    @JoinColumn(name = "lime_id")
    LimeEntity lime;

    @Column(name = "amount")
    Long amount;

    @Column(name = "date_of_receipt")
    LocalDateTime dateOfReceipt;

    @Column(name = "is_check")
    Boolean isCheck;


    @Column(name = "is_accept")
    Boolean isAccept;

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

    public Boolean getCheck() {
        return isCheck;
    }

    public Boolean getAccept() {
        return isAccept;
    }
}
