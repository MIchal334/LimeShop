package com.lime.lime.shop.model.entity;

import com.lime.lime.shop.model.dto.LimeDTO;

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

    public LimeEntity(LimeDTO newLime, UserEntity user) {
        this.owner = user;
        this.type = newLime.getType();
        this.amount = newLime.getAmount();
        this.isDeleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Set<OrderEntity> getOrdersWithLime() {
        return ordersWithLime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}
