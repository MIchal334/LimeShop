package com.lime.lime.shop.model.dto;

import com.lime.lime.shop.model.entity.UserEntity;

public class UserDTOForAdmin  extends UserDTO{
    private boolean isDeleted;
    private String role;

    public UserDTOForAdmin(UserEntity user) {
        super(user);
        this.isDeleted = user.isDeleted();
        this.role = user.getRole().getRoleName();
    }
}
