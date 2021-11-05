package com.lime.lime.shop.lime;

import com.lime.lime.shop.order.OrderEntity;
import com.lime.lime.shop.user.UserEntity;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    UserEntity owner;

    @OneToMany(mappedBy = "lime", cascade = CascadeType.ALL)
    Set<OrderEntity> ordersWithLime;

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

    public UserEntity getOwner() {
        return owner;
    }
}
