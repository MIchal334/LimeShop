package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }




    public List<OrderEntity> getOrderListByStatusAndUserId(OrderStatusType statusType, Long id) {
        return orderRepository.getOrderByStatusAndUserId(statusType.name(),id);
    }

    public OrderEntity prepareOrderToAccept(Long userId,Long orderId){
        OrderEntity order = getOrderById(orderId);
        isDealerOfOrder(userId,order);
        chekIfOrderIsToAccept(order);
        return order;
    }

    public OrderEntity getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotExistsException("Order with this id not exist"));
    }

    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    private void isDealerOfOrder(Long userId, OrderEntity order){
        if(order.getProducer().getId() != userId) {
            throw new IllegalStateException("This is not your order");
        }

    }

    private void chekIfOrderIsToAccept(OrderEntity orderEntity){
        if(!orderEntity.getStatus().equals(OrderStatusType.WAITING)){
            throw new IllegalStateException("You can't accept this. Status is "+orderEntity.getStatus().getStatusName());
        }
    }


}
