package com.lime.lime.shop.model.entity;

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

    @Column(name = "is_check")
    private Boolean isCheck;


    @Column(name = "is_accept")
    private Boolean isAccept;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

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
