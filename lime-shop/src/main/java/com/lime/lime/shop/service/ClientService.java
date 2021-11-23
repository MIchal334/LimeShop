package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.ClientPreducentRelation;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final  UserService userService;

    public ClientService(UserService userService) {
        this.userService = userService;
    }

    
    
    public List<UserDTO> getProducerAssignmentToClient() {
        UserEntity user = userService.handleCurrentUser();
        return user.getMyDealer()
                .stream()
                .map(ClientPreducentRelation::getClient)
                .filter(o -> !o.isDeleted())
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
        UserEntity user = userService.handleCurrentUser();
    }
}
