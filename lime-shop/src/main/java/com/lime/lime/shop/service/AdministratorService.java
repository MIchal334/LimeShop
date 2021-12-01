package com.lime.lime.shop.service;


import com.lime.lime.shop.exceptionHandler.exception.ResourceNotExistsException;
import com.lime.lime.shop.model.dto.TransactionDTO;
import com.lime.lime.shop.model.dto.UserDTOForAdmin;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.OrderRepository;
import com.lime.lime.shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministratorService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final  UserService userService;

    public AdministratorService(UserRepository userRepository, OrderRepository orderRepository, UserService userService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<UserDTOForAdmin> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserDTOForAdmin::new)
                .collect(Collectors.toList());
    }


    public List<TransactionDTO> getAllTransactionOfUser(Long userId) {
        return orderRepository.getOrderWithUserById(userId)
                .stream()
                .map(TransactionDTO::new)
                .sorted(Comparator.comparing(TransactionDTO::getDateOfReceipt))
                .collect(Collectors.toList());
    }

    public void deleteUserByAdmin(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotExistsException("This user not exist"));
        userService.deleteUser(Optional.of(user));
    }

}
