package com.lime.lime.shop.repository;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("from OrderEntity o where o.producer.id = :id")
    List<OrderEntity> getActuallyOrdersByProducerId(@Param("id") Long id) ;

    @Query("from  OrderEntity o where o.producer.id =:userId and o.status.statusName = :status")
    List<OrderEntity> getOrderByStatusAndUserId(@Param("status") String status, @Param("userId") Long userId);

    @Query("from OrderEntity o where o.producer.id = :userId and o.dateOfReceipt > :now")
    List<ProducerOrderReadModel> findAllHistoryOrdersByUserId(@Param("now") LocalDateTime now,@Param("userId") UserEntity userId);

    @Query("from OrderEntity o where  o.dateOfReceipt > :now")
    List<OrderEntity> getAllOldOrderToCanceled(LocalDateTime now);
}
