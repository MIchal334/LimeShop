package com.lime.lime.shop.dictionaryTable;

import com.lime.lime.shop.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "producnet_client")
public class ClientPreducentRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producnet_id")
    private UserEntity producer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity client;

    public ClientPreducentRelation() {
    }

    public UserEntity getProducer() {
        return producer;
    }

    public UserEntity getClient() {
        return client;
    }
}
