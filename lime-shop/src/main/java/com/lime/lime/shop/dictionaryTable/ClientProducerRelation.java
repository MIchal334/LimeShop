package com.lime.lime.shop.dictionaryTable;

import com.lime.lime.shop.model.entity.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "producnet_client")
public class ClientProducerRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producnet_id")
    private Long producer;

    @Column(name = "client_id")
    private Long client;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public ClientProducerRelation() {
    }

    public ClientProducerRelation(Long clientId, Long producerId) {
        this.producer = producerId;
        this.client = clientId;
        this.isDeleted = false;
    }

    public Long getProducer() {
        return producer;
    }

    public void setProducer(Long producer) {
        this.producer = producer;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
