package com.lime.lime.shop.lime;

import javax.persistence.*;

@Entity
@Table(name = "lime")
public class LimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "amount")
    Integer amount;

    @Column(name = "owner_id")
    Long owner_id;

    public LimeEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }

    public Long getOwner_id() {
        return owner_id;
    }
}
