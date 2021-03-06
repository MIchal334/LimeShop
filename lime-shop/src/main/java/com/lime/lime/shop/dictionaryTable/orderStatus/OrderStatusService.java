package com.lime.lime.shop.dictionaryTable.orderStatus;

import com.lime.lime.shop.model.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public List<OrderEntity> getAllOrderByStatusAndUserId(String statusName, Long userId){
        return orderStatusRepository.getOrderByStatusAndUserId(statusName,userId);
    }
}
