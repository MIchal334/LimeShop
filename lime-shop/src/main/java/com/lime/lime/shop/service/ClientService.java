package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusRepository;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.OrderReadModel;
import com.lime.lime.shop.model.dto.OrderWriteModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.ClientProducerRepository;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final UserService userService;
    private final ClientProducerRepository clientProducerRepository;
    private final LimeService limeService;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderService orderService;


    public ClientService(UserService userService, ClientProducerRepository clientProducerRepository, LimeService limeService, OrderStatusRepository orderStatusRepository, OrderService orderService) {
        this.userService = userService;
        this.clientProducerRepository = clientProducerRepository;
        this.limeService = limeService;
        this.orderStatusRepository = orderStatusRepository;
        this.orderService = orderService;
    }


    public List<UserDTO> getProducerAssignmentToClient() {
        UserEntity user = userService.handleCurrentUser();
        List<Long> producersAssignToUser = clientProducerRepository.getAllProducerByClientId(user.getId());
        return userService.getAllUserByRoleName(RoleType.PRODUCER.name())
                .stream()
                .filter(o -> !o.isDeleted())
                .filter(o -> producersAssignToUser.contains(o.getId()))
                .sorted(Comparator.comparing(o -> computeDistance(user.getAddress(), o.getAddress())))
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllProducer() {
        UserEntity currentUser = userService.handleCurrentUser();
        return userService.getAllUserByRoleName(RoleType.PRODUCER.name())
                .stream()
                .filter(o -> !o.isDeleted())
                .sorted(Comparator.comparing(o -> computeDistance(currentUser.getAddress(), o.getAddress())))
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }


    public void assignNewDealer(Long producerId) {
        UserEntity currentUser = userService.handleCurrentUser();
        UserEntity dealer = userService.getUserByIdAndRole(producerId, RoleType.PRODUCER);
        ClientProducerRelation newRelation = new ClientProducerRelation(currentUser.getId(), dealer.getId());
        clientProducerRepository.save(newRelation);

    }

    public void deleteAssignDealer(Long producerId) {
        UserEntity currentUser = userService.handleCurrentUser();
        UserEntity dealer = userService.getUserByIdAndRole(producerId, RoleType.PRODUCER);
        ClientProducerRelation relation = getClientProducerRelation(currentUser.getId(), dealer.getId());
        relation.setDeleted(true);
        clientProducerRepository.save(relation);

    }

    public List<LimeDTO> getAllLimeByDealerId(Long producerId) {
        UserEntity user = userService.handleCurrentUser();
        getClientProducerRelation(user.getId(), producerId);
        List<LimeDTO> listOfLime = limeService.getAllLimeByProducerId(producerId);
        return listOfLime;
    }


    public List<OrderReadModel> getAllOrderOfClient() {
        UserEntity user = userService.handleCurrentUser();
        List<OrderEntity> allOrderOfClient = orderService.getAcctuallyOrderForClient(user.getId());
        List<OrderReadModel> listOfOrder = allOrderOfClient.stream()
                .map(o -> new OrderReadModel(user, o.getLime(), o))
                .sorted(Comparator.comparing(OrderReadModel::getDateOfReceipt))
                .collect(Collectors.toList());
        return listOfOrder;

    }

    public void getNewOrder(Long producerId, OrderWriteModel order) {
        UserEntity currentUser = userService.handleCurrentUser();
        getClientProducerRelation(currentUser.getId(), producerId);
        UserEntity producer = userService.getUserByIdAndRole(producerId, RoleType.PRODUCER);
        LimeEntity lime = limeService.getNewOrder(order.getLimeId(), order.getAmount());

        OrderStatusEntity orderStatus = orderStatusRepository.getOrderStatusByName(OrderStatusType.WAITING.name());
        OrderEntity orderToSave = new OrderEntity(order, currentUser, producer, lime, orderStatus);

        orderService.save(orderToSave);


    }

    public List<OrderReadModel> getAllOrderToHistory() {
        UserEntity user = userService.handleCurrentUser();
        return orderService.getAllHistoryOrders(user, RoleType.CLIENT);
    }

    public void deleteOrderById(Long orderId) {
        UserEntity user = userService.handleCurrentUser();
        OrderEntity order = orderService.prepareOrderToChangeStatus(user.getId(), orderId, OrderStatusType.CANCELED, RoleType.CLIENT);
        order.setStatus(orderStatusRepository.getOrderStatusByName(OrderStatusType.CANCELED.name()));
        orderService.save(order);

    }


    private ClientProducerRelation getClientProducerRelation(Long clientId, Long producerId) {
        ClientProducerRelation relation = clientProducerRepository.findByClientAndProducerId(clientId, producerId)
                .orElseThrow(() -> new IllegalStateException("This dealer is not assign to you"));
        return relation;
    }


    private float computeDistance(AddressEntity clientAddress, AddressEntity producerAddress) {
        float distance;
        float earthRadius = 6371;
        float clientLon = (float) (clientAddress.getLon() * Math.PI/180);
        float clientLat = (float) (clientAddress.getLat() * Math.PI/180);
        float producerLon = (float) (producerAddress.getLon() * Math.PI/180);
        float producerLat = (float) (producerAddress.getLat() * Math.PI/180);


        float x = (float) ((producerLon - clientLon) * Math.cos((producerLat + clientLat) / 2));
        float y = (producerLat - clientLat);

        distance = (float) (Math.sqrt(x * x + y * y) * earthRadius);
        return distance;
    }


}
