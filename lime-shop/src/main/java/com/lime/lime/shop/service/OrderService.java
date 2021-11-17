package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@EnableScheduling
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<OrderEntity> getOrderListByStatusAndUserId(OrderStatusType statusType, Long id) {
        return orderRepository.getOrderByStatusAndUserId(statusType.name(), id);
    }

    public OrderEntity prepareOrderToChangeStatus(Long userId, Long orderId, OrderStatusType toType) {
        OrderEntity order = getOrderById(orderId);
        isDealerOfOrder(userId, order);

        switch (toType) {
            case ACCEPTED:
                chekIfOrderIsToAccept(toType);
            case DONE:
                chekIfOrderIsToDone(toType);
            case CANCELED:
                chekIfOrderIsToCanceled(toType);

        }
        return order;
    }

    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotExistsException("Order with this id not exist"));
    }

    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    public List<ProducerOrderReadModel> getAllHistoryOrders(UserEntity userId) {
        return orderRepository.findAllHistoryOrdersByUserId(LocalDateTime.now(),userId);
    }

    private void isDealerOfOrder(Long userId, OrderEntity order) {
        if (order.getProducer().getId() != userId) {
            throw new IllegalStateException("This is not your order");
        }

    }

    private void chekIfOrderIsToAccept(OrderStatusType toType) {
        if (!toType.equals(OrderStatusType.WAITING)) {
            throw new IllegalStateException("You can't accept this. Status is " + toType.name());
        }
    }

    private void chekIfOrderIsToDone(OrderStatusType toType) {
        if (!toType.equals(OrderStatusType.ACCEPTED)) {
            throw new IllegalStateException("You can't set Done this. Status is " + toType);
        }
    }

    private void chekIfOrderIsToCanceled(OrderStatusType toType) {
        if (toType.equals(OrderStatusType.CANCELED)) {
            throw new IllegalStateException("This order has already canceled");
        }
    }

    @Scheduled(cron =  "${corn.setCanceled}")
    private void setNotAcceptedOldOrderToCanceled(){
        System.out.println("Zaczynam skan");
        List<OrderEntity> takeAllOrdersToCanceled = orderRepository.getAllOldOrderToCanceled(LocalDateTime.now());
        takeAllOrdersToCanceled.forEach(x -> x.setStatus(new OrderStatusEntity(OrderStatusType.CANCELED)));
    }

}
