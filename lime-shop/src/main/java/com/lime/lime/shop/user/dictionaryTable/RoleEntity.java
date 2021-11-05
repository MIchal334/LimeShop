package com.lime.lime.shop.user.dictionaryTable;

import com.lime.lime.shop.user.UserEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    Short id;

    @Column(name = "role_name")
    String roleName;

    @OneToMany(mappedBy = "role")
    Set<UserEntity> userEntitySet;

    public RoleEntity() {
    }

    public Short getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
