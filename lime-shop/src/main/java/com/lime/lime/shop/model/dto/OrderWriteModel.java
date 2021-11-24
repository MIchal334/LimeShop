package com.lime.lime.shop.model.dto;

import java.time.LocalDateTime;

public class OrderWriteModel {

    private Long limeId;
    private Long amount;
    private LocalDateTime dateOfReceipt;


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
