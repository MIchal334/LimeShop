package com.lime.lime.shop.user.dictionaryTable;

import javax.persistence.*;

@Entity
@Table(name = "producnet_client")
public class ClientPreducentRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "producnet_id")
    Long producnetId;

    @Column(name = "client_id")
    Long clientId;
}
