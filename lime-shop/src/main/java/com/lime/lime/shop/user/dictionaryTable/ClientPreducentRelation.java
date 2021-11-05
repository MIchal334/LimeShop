package com.lime.lime.shop.user.dictionaryTable;

import com.lime.lime.shop.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "producnet_client")
public class ClientPreducentRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "producnet_id")
    UserEntity producer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    UserEntity client;

    public ClientPreducentRelation() {
    }

    public UserEntity getProducer() {
        return producer;
    }

    public UserEntity getClient() {
        return client;
    }
}
