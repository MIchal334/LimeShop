package com.lime.lime.shop.dictionaryTable.orderStatus;

import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_status")
public class OrderStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "status")
    private String statusName;

    @OneToMany(mappedBy = "status")
    private Set<OrderEntity> orders;

    public OrderStatusEntity() {
    }

    public OrderStatusEntity(OrderStatusType statusName) {
        this.statusName = statusName.name();
    }

    public Short getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }
}
