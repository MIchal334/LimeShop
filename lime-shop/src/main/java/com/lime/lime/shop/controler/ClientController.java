package com.lime.lime.shop.controler;

import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

   private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/allDealer")
    ResponseEntity<List<UserDTO>> getAllProducer(){
        List<UserDTO> result = clientService.getAllProducer();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/myDealer")
    ResponseEntity<List<UserDTO>> getProducerAssignToClient(){
        List<UserDTO> result = clientService.getProducerAssignmentToClient();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/allDealer/{id}")
    ResponseEntity<?> assignNewProducerToClient(@PathVariable(name = "id") Long producerId ){
        clientService.assignNewDealer(producerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/myDealer/{id}")
    ResponseEntity<?> deleteAssignDealer(@PathVariable(name = "id")Long producerId){
        clientService.deleteAssignDealer(producerId);
        return ResponseEntity.ok().build();
    }

}
