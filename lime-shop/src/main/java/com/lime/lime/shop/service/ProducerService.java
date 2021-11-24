package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.ClientProducerRepository;
import com.lime.lime.shop.repository.LimeRepository;
import com.lime.lime.shop.validators.LimeDataValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final UserService userService;
    private final LimeRepository limeRepository;
    private final LimeDataValidator limeDataValidator;
    private final OrderService orderService;
    private final ClientProducerRepository clientProducerRepository;


    public ProducerService(UserService userService, LimeRepository limeRepository, LimeDataValidator limeDataValidator, OrderService orderService, ClientProducerRepository clientProducerRepository) {
        this.userService = userService;
        this.limeRepository = limeRepository;
        this.limeDataValidator = limeDataValidator;
        this.orderService = orderService;
        this.clientProducerRepository = clientProducerRepository;
    }


    public List<ProducerOrderReadModel> getOrdersByStatus(OrderStatusType statusType) {
        UserEntity user = userService.handleCurrentUser();

        List<ProducerOrderReadModel> orderList = orderService.getOrderListByStatusAndUserId(statusType, user.getId())
                .stream()
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

    public void addNewLime(LimeDTO newLime) {
        UserEntity user = userService.handleCurrentUser();
        limeDataValidator.validData(newLime, user, false);
        LimeEntity limeToSave = new LimeEntity(newLime, user);
        limeRepository.save(limeToSave);

    }

    public void updateAmountOfLime(Long id, Integer newResource) {
        UserEntity user = userService.handleCurrentUser();
        LimeEntity lime = getLimeById(id);
        int oldAmount = lime.getAmount();
        lime.setAmount(newResource);

        limeDataValidator.validData(new LimeDTO(lime), user, true);
        lime.setAmount(oldAmount + newResource);
        limeRepository.save(lime);
    }

    @Transactional
    public void deleteOfLime(Long id) {
        UserEntity user = userService.handleCurrentUser();
        LimeEntity lime = getLimeById(id);
        limeDataValidator.ownerValidation(lime.getId(), user.getId());
        lime.setDeleted(true);
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

    public List<ProducerOrderReadModel> getAllOrderToHistory() {
        UserEntity user = userService.handleCurrentUser();
        return orderService.getAllHistoryOrders(user);
    }

    public void changeOrderStatusById(Long orderId, OrderStatusType changeToStatus) {
        UserEntity user = userService.handleCurrentUser();
        OrderEntity order = orderService.prepareOrderToChangeStatus(user.getId(), orderId, changeToStatus);
        order.setStatus(new OrderStatusEntity(changeToStatus));
        orderService.save(order);
    }


    private LimeEntity getLimeById(Long id) {
        return limeRepository.getLimeById(id)
                .orElseThrow(() -> new ResourceNotExistsException("This lime not exist"));
    }


}
