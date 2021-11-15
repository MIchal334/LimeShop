package com.lime.lime.shop.repository;

import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("from OrderEntity o where o.producer.id = :id")
    List<OrderEntity> getActuallyOrdersByProducerId(@Param("id") Long id) ;

}
