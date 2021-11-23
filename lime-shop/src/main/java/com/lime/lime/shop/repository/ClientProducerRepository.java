package com.lime.lime.shop.repository;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientProducerRepository extends JpaRepository<ClientProducerRelation,Long> {
    @Query("select r.producer from ClientProducerRelation r where r.client = :userId")
    List<Long> getAllByClientId(@Param("userId") Long id);
}
