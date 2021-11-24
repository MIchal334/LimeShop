package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.dto.OrderReadModel;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType.*;

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
                chekIfOrderIsToAccept(order.getStatus().getStatusName());
                break;
            case DONE:
                chekIfOrderIsToDone(order.getStatus().getStatusName());
                break;
            case CANCELED:
                chekIfOrderIsToCanceled(order.getStatus().getStatusName());
                break;
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

    public List<OrderReadModel> getAllHistoryOrders(UserEntity user, RoleType roleType) {
        return orderRepository
                .findAllHistoryOrdersByUserId(LocalDateTime.now(), user.getId())
                .stream()
                .map(o -> new OrderReadModel(getUserFromOrder(roleType, o), o.getLime(), o))
                .collect(Collectors.toList());
    }

    private void isDealerOfOrder(Long userId, OrderEntity order) {
        if (order.getProducer().getId() != userId) {
            throw new IllegalStateException("This is not your order");
        }
    }

    private UserEntity getUserFromOrder(RoleType roleType, OrderEntity order) {
        if (roleType.name().equals("PRODUCER")) {
            return order.getClient();
        } else {
            return order.getProducer();
        }
    }

    private void chekIfOrderIsToAccept(String acctualyStatus) {
        if (!acctualyStatus.equals(WAITING.name())) {
            throw new IllegalStateException("You can't accept this. Status is " + acctualyStatus);
        }
    }

    private void chekIfOrderIsToDone(String acctualyStatus) {
        if (!acctualyStatus.equals(ACCEPTED.name())) {
            throw new IllegalStateException("You can't set Done this. Status is " + acctualyStatus);
        }
    }

    private void chekIfOrderIsToCanceled(String acctualyStatus) {
        if (acctualyStatus.equals(CANCELED.name())) {
            throw new IllegalStateException("This order has already canceled");
        }
        if (acctualyStatus.equals(DONE.name())) {
            throw new IllegalStateException("This order has already done");
        }
    }

    @Scheduled(cron = "${corn.setCanceled}")
    private void setNotAcceptedOldOrderToCanceled() {
        System.out.println("Zaczynam skan");
        List<OrderEntity> takeAllOrdersToCanceled = orderRepository.getAllOldOrderToCanceled(LocalDateTime.now());
        takeAllOrdersToCanceled.forEach(x -> x.setStatus(new OrderStatusEntity(CANCELED)));
    }

}
