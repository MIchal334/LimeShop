package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusRepository;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.OrderReadModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.ClientProducerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final UserService userService;
    private final OrderService orderService;
    private final ClientProducerRepository clientProducerRepository;
    private final LimeService limeService;
    private final OrderStatusRepository orderStatusRepository;


    public ProducerService(UserService userService, OrderService orderService, ClientProducerRepository clientProducerRepository, LimeService limeService, OrderStatusRepository orderStatusRepository) {
        this.userService = userService;
        this.orderService = orderService;
        this.clientProducerRepository = clientProducerRepository;
        this.limeService = limeService;
        this.orderStatusRepository = orderStatusRepository;
    }


    public List<OrderReadModel> getOrdersByStatus(OrderStatusType statusType) {
        UserEntity user = userService.handleCurrentUser();

        List<OrderReadModel> orderList = orderService.getOrderListByStatusAndUserId(statusType, user.getId())
                .stream()
                .map(x -> new OrderReadModel(x.getClient(),x.getLime(),x))
                .sorted(Comparator.comparing(OrderReadModel::getDateOfReceipt))
                .collect(Collectors.toList());

        return orderList;
    }

    public List<LimeDTO> getAllLimeOfProducer() {
        UserEntity user = userService.handleCurrentUser();
        List<LimeDTO> listOfLime = limeService.getAllLimeByProducerId(user.getId());
        return listOfLime;
    }

    public void addNewLime(LimeDTO newLime) {
        UserEntity user = userService.handleCurrentUser();
        limeService.saveNewLime(newLime,user);

    }

    public void updateAmountOfLime(Long limeId, Integer newResource) {
        UserEntity user = userService.handleCurrentUser();
        limeService.updateLime(limeId,newResource,user);
    }

    @Transactional
    public void deleteOfLime(Long limeId) {
        UserEntity user = userService.handleCurrentUser();
        limeService.deleteLimeById(limeId,user.getId());
    }


    public List<UserDTO> getAllClients() {
        UserEntity user = userService.handleCurrentUser();
        List<Long> clientAssignToUser = clientProducerRepository.getAllClientByProducerId(user.getId());

        return  userService.getAllUserByRoleName(RoleType.CLIENT.name())
                .stream()
                .filter(o -> !o.isDeleted())
                .filter(o -> clientAssignToUser.contains(o.getId()))
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<OrderReadModel> getAllOrderToHistory() {
        UserEntity user = userService.handleCurrentUser();
        return orderService.getAllHistoryOrders(user,RoleType.PRODUCER);
    }

    public void changeOrderStatusById(Long orderId, OrderStatusType changeToStatus) {
        UserEntity user = userService.handleCurrentUser();
        OrderEntity order = orderService.prepareOrderToChangeStatus(user.getId(), orderId, changeToStatus,RoleType.PRODUCER);
        order.setStatus(orderStatusRepository.getOrderStatusByName(changeToStatus.name()));
        orderService.save(order);
    }





}
