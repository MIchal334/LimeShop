package com.lime.lime.shop.dictionaryTable.orderStatus;

import com.lime.lime.shop.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity,Short> {

    @Query("select s.orders from OrderStatusEntity s inner join s.orders o " +
            "where s.statusName = :status and o.producer.id =:userId")
    List<OrderEntity> getOrderByStatusAndUserId(@Param("status") String status, @Param("userId") Long userId);

}
