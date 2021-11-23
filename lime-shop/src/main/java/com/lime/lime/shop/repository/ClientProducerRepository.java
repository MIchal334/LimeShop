package com.lime.lime.shop.repository;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProducerRepository extends JpaRepository<ClientProducerRelation,Long> {
}
