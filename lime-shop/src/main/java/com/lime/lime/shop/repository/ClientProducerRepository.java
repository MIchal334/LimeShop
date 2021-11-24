package com.lime.lime.shop.repository;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientProducerRepository extends JpaRepository<ClientProducerRelation,Long> {
    @Query("select r.producer from ClientProducerRelation r where r.client = :userId and r.isDeleted = false ")
    List<Long> getAllProducerByClientId(@Param("userId") Long id);

    @Query("select r.client from ClientProducerRelation r where r.producer = :userId and r.isDeleted = false ")
    List<Long> getAllClientByProducerId(@Param("userId") Long id);

    @Query("from ClientProducerRelation r where r.client = :clientId and r.producer = :producerId and r.isDeleted = false ")
    Optional<ClientProducerRelation> findByClientAndProducerId(@Param("clientId") Long clientId,@Param("producerId") Long producerId);

    @Query("from ClientProducerRelation r where r.client = :id or r.producer = :id")
    List<ClientProducerRelation> getAllRelationByUserId(@Param("id") Long userId);
}
