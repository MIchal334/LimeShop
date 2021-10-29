package com.lime.lime.shop.order;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "producent_id")
    Long producentId;

    @Column(name = "client_id")
    Long clientId;

    @Column(name = "lime_id")
    Long limeId;

    @Column(name = "amount")
    Long amount;

    @Column(name = "date_of_receipt")
    LocalDateTime dateOfReceipt;

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getProducentId() {
        return producentId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getLimeId() {
        return limeId;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getDateOfReceipt() {
        return dateOfReceipt;
    }
}
