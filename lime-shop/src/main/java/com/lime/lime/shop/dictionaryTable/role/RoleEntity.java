package com.lime.lime.shop.dictionaryTable.role;

import com.lime.lime.shop.model.entity.UserEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    private Short id;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<UserEntity> userEntitySet;

    public RoleEntity() {
    }

    public Short getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
