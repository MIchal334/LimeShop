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
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "lime", cascade = CascadeType.ALL)
    private Set<OrderEntity> ordersWithLime;

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
