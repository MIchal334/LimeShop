package com.lime.lime.shop.service;

import com.lime.lime.shop.dictionaryTable.ClientProducerRelation;
import com.lime.lime.shop.dictionaryTable.role.RoleType;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.ClientProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final  UserService userService;
    private final ClientProducerRepository clientProducerRepository;


    public ClientService(UserService userService, ClientProducerRepository clientProducerRepository) {
        this.userService = userService;
        this.clientProducerRepository = clientProducerRepository;
    }

    
    
    public List<UserDTO> getProducerAssignmentToClient() {
        UserEntity user = userService.handleCurrentUser();
        return  null;
//        return user.getMyDealer()
//                .stream()
//                .map(ClientProducerRelation::getClient)
//                .filter(o -> !o.isDeleted())
//                .map(UserDTO::new)
//                .collect(Collectors.toList());
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
}
