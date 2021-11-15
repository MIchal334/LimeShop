package com.lime.lime.shop.service;

import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public ProducerService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<ProducerOrderReadModel> getActuallyOrders() {
        UserEntity user = userService.handleCurrentUser();

        List<ProducerOrderReadModel> orderList = orderRepository.getActuallyOrdersForProducer(user.getId())
                .stream()
                .filter(x -> !x.isDeleted() && x.isAccept() && !x.isCheck())
                .map(x -> new ProducerOrderReadModel(x, x.getClient().getAddress()))
                .collect(Collectors.toList());

        return orderList;
    }
}
