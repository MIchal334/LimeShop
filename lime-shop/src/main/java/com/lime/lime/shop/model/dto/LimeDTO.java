package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;

import javax.persistence.*;
import java.util.Set;

public class LimeDTO {

    private Long id;
    private String type;
    private Integer amount;

    public LimeDTO() {
    }

    public LimeDTO(LimeEntity lime) {
        this.id = lime.getId();
        this.type = lime.getType();
        this.amount = lime.getAmount();
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
}
