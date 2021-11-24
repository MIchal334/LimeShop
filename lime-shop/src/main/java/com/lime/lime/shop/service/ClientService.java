package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.OrderReadModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.ClientProducerRepository;
import com.lime.lime.shop.repository.LimeRepository;
import com.lime.lime.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final  UserService userService;
    private final ClientProducerRepository clientProducerRepository;
    private final LimeRepository limeRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;



    public ClientService(UserService userService, ClientProducerRepository clientProducerRepository, LimeRepository limeRepository, OrderRepository orderRepository, OrderService orderService) {
        this.userService = userService;
        this.clientProducerRepository = clientProducerRepository;
        this.limeRepository = limeRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    
    
    public List<UserDTO> getProducerAssignmentToClient() {
        UserEntity user = userService.handleCurrentUser();
        List<Long> producersAssignToUser = clientProducerRepository.getAllProducerByClientId(user.getId());
        return userService.getAllUserByRoleName(RoleType.PRODUCER.name())
                .stream()
                .filter(o -> !o.isDeleted())
                .filter(o -> producersAssignToUser.contains(o.getId()))
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllProducer() {
        return userService.getAllUserByRoleName(RoleType.PRODUCER.name())
                .stream()
                .filter(o -> !o.isDeleted())
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public void assignNewDealer(Long producerId) {
        UserEntity currentUser = userService.handleCurrentUser();
        UserEntity dealer = userService.getUserByIdAndRole(producerId,RoleType.PRODUCER);
        ClientProducerRelation newRelation = new ClientProducerRelation(currentUser.getId(),dealer.getId());
        clientProducerRepository.save(newRelation);

    }

    public void deleteAssignDealer(Long producerId) {
        UserEntity currentUser = userService.handleCurrentUser();
        UserEntity dealer = userService.getUserByIdAndRole(producerId,RoleType.PRODUCER);
        ClientProducerRelation relation = getClientProducerRelation(currentUser.getId(),dealer.getId());
        relation.setDeleted(true);
        clientProducerRepository.save(relation);

    }

    public List<LimeDTO> getAllLimeByDealerId(Long producerId) {
        UserEntity user = userService.handleCurrentUser();
        getClientProducerRelation(user.getId(),producerId);
        List<LimeDTO> listOfLime = limeRepository.getAllLimeByProducerId(producerId)
                .stream()
                .filter(x -> !x.isDeleted())
                .map(LimeDTO::new)
                .collect(Collectors.toList());
        return listOfLime;
    }


    public List<OrderReadModel> getAllOrderOfClient() {
        UserEntity user = userService.handleCurrentUser();

        List<OrderEntity> allOrderOfClient = orderRepository.findAllByClientId(user.getId());
        List<OrderEntity> historyOrderOfClient = orderRepository.findAllHistoryOrdersByUserId(LocalDateTime.now(),user);
        allOrderOfClient.removeAll(historyOrderOfClient);

        List<OrderReadModel> listOfOrder = allOrderOfClient.stream()
                .map(o -> new OrderReadModel(user,o.getLime(),o))
                .collect(Collectors.toList());
        return listOfOrder;
    }


    private ClientProducerRelation getClientProducerRelation(Long clientId, Long producerId){
       ClientProducerRelation relation = clientProducerRepository.findByClientAndProducerId(clientId,producerId)
        .orElseThrow(() -> new IllegalStateException("This dealer is not assign to you"));
        return relation;
    }


}
