package com.lime.lime.shop.controler;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.OrderReadModel;
import com.lime.lime.shop.model.dto.OrderWriteModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.service.ClientService;
import com.lime.lime.shop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/client")
@RolesAllowed("ROLE_CLIENT")
public class ClientController {

    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/allDealer")
    ResponseEntity<List<UserDTO>> getAllProducer() {
        List<UserDTO> result = clientService.getAllProducer();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/myDealer")
    ResponseEntity<List<UserDTO>> getProducerAssignToClient() {
        List<UserDTO> result = clientService.getProducerAssignmentToClient();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/myDealer/{id}")
    ResponseEntity<List<LimeDTO>> getLimeOfDealer(@PathVariable(name = "id") Long producerId) {
        List<LimeDTO> result = clientService.getAllLimeByDealerId(producerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    ResponseEntity<List<OrderReadModel>> getOrderOfClient() {
        List<OrderReadModel> result = clientService.getAllOrderOfClient();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ordersHistory")
    ResponseEntity<List<OrderReadModel>> getOrdersHistory() {
        List<OrderReadModel> result = clientService.getAllOrderToHistory();
        return ResponseEntity.ok(result);
    }


    @PostMapping("/myDealer/{id}")
    ResponseEntity<?> getNewOrder(@PathVariable(name = "id") Long producerId, @RequestBody OrderWriteModel order) {
        clientService.getNewOrder(producerId, order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/allDealer/{id}")
    ResponseEntity<?> assignNewProducerToClient(@PathVariable(name = "id") Long producerId) {
        clientService.assignNewDealer(producerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/myDealer/{id}")
    ResponseEntity<?> deleteAssignDealer(@PathVariable(name = "id") Long producerId) {
        clientService.deleteAssignDealer(producerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrderByClient(@PathVariable(name = "id") Long orderId) {
        clientService.deleteOrderById(orderId);
        return ResponseEntity.ok().build();
    }

}
