package com.lime.lime.shop.producent;

import javax.persistence.*;

@Entity
@Table(name = "producents")
public class ProducentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "company_name")
    String companyName;

    public ProducentEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCompanyName() {
        return companyName;
    }
}
