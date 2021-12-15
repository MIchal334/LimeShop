package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.Set;

public class LimeDTO {

    private Long id;
    private String type;
    private Integer amount;
    private Integer price;

    public LimeDTO() {
    }

    public LimeDTO(LimeEntity lime) {
        this.id = lime.getId();
        this.type = lime.getType();
        this.amount = lime.getAmount();
        this.price = lime.getPrice();
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
