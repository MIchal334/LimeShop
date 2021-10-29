package com.lime.lime.shop.user.dictionaryTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    Short id;

    @Column(name = "role_name")
    String roleName;

    public RoleEntity() {
    }

    public Short getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
