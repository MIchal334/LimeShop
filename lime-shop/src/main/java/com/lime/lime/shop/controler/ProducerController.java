package com.lime.lime.shop.controler;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producer")
//@RolesAllowed("PRODUCER")
public class ProducerController {

   private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    ResponseEntity<List<ProducerOrderReadModel>> getAllActuallyOrders(){
        List<ProducerOrderReadModel> result = producerService.getOrdersByStatus(OrderStatusType.ACCEPTED);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order")
    ResponseEntity<List<ProducerOrderReadModel>> getAllWaitingOrders(){
        List<ProducerOrderReadModel> result = producerService.getOrdersByStatus(OrderStatusType.WAITING);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/order/{id}")
    ResponseEntity<?> acceptOrderById(@PathVariable(name = "id") Long id){
        producerService.acceptOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/resource")
    ResponseEntity<List<LimeDTO>> getAllLimeOfProducer(){
        List<LimeDTO> result = producerService.getAllLimeOfProducer();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/resource")
    ResponseEntity<?> addNewLime(@RequestBody LimeDTO newLime){
        producerService.addNewLime(newLime);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/resource/{id}")
    ResponseEntity<?> updateAmountOfLime(@PathVariable(name = "id") Long id ,@RequestBody LimeDTO newResource){
        producerService.updateAmountOfLime(id,newResource.getAmount());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/resource/{id}")
    ResponseEntity<?> deleteOfLime(@PathVariable(name = "id") Long id){
        producerService.deleteOfLime(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client")
    ResponseEntity<List<UserDTO>> getAllClients(){
        List<UserDTO> result = producerService.getAllClients();
        return ResponseEntity.ok(result);
    }




}
