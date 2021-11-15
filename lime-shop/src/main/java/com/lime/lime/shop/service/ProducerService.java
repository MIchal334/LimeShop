package com.lime.lime.shop.service;

import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final LimeRepository limeRepository;

    public ProducerService(OrderRepository orderRepository, UserService userService, LimeRepository limeRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.limeRepository = limeRepository;
    }

    public List<ProducerOrderReadModel> getActuallyOrders() {
        UserEntity user = userService.handleCurrentUser();

        List<ProducerOrderReadModel> orderList = orderRepository.getActuallyOrdersByProducerId(user.getId())
                .stream()
                .filter(x -> !x.isDeleted() && x.isAccept() && !x.isCheck())
                .map(x -> new ProducerOrderReadModel(x, x.getClient().getAddress()))
                .collect(Collectors.toList());

        return orderList;
    }

    public List<LimeDTO> getAllLimeOfProducer() {
        UserEntity user = userService.handleCurrentUser();
        List<LimeDTO> listOfLime = limeRepository.getAllLimeByProducerId(user.getId())
                .stream()
                .filter(x -> !x.isDeleted())
                .map(LimeDTO::new)
                .collect(Collectors.toList());
        return listOfLime;
    }
}
