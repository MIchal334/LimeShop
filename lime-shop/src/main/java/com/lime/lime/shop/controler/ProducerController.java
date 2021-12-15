package com.lime.lime.shop.controler;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusType;
import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.OrderReadModel;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/producer")
@RolesAllowed("ROLE_PRODUCER")
@CrossOrigin
public class ProducerController {

   private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    ResponseEntity<List<OrderReadModel>> getAllActuallyOrders(){
        List<OrderReadModel> result = producerService.getOrdersByStatus(OrderStatusType.ACCEPTED);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order")
    ResponseEntity<List<OrderReadModel>> getAllWaitingOrders(){
        List<OrderReadModel> result = producerService.getOrdersByStatus(OrderStatusType.WAITING);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/orderHistory")
    ResponseEntity<List<OrderReadModel>> getOrdersHistory(){
        List<OrderReadModel> result = producerService.getAllOrderToHistory();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/resource")
    ResponseEntity<List<LimeDTO>> getAllLimeOfProducer(){
        List<LimeDTO> result = producerService.getAllLimeOfProducer();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/client")
    ResponseEntity<List<UserDTO>> getAllClients(){
        List<UserDTO> result = producerService.getAllClients();
        return ResponseEntity.ok(result);
    }



    @PostMapping("/resource")
    ResponseEntity<?> addNewLime(@RequestBody LimeDTO newLime){
        producerService.addNewLime(newLime);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/order/{id}")
    ResponseEntity<?> acceptOrderById(@PathVariable(name = "id") Long id){
        producerService.changeOrderStatusById(id,OrderStatusType.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/order/{id}")
    ResponseEntity<?> setOrderDoneById(@PathVariable(name = "id") Long id){
        producerService.changeOrderStatusById(id,OrderStatusType.DONE);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/resource/{id}")
    ResponseEntity<?> updateLime(@PathVariable(name = "id") Long id , @RequestBody LimeDTO newResource){
        producerService.updateLime(id,newResource);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/order/{id}")
    ResponseEntity<?> cancelOrderById(@PathVariable(name = "id") Long id){
        producerService.changeOrderStatusById(id,OrderStatusType.CANCELED);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/resource/{id}")
    ResponseEntity<?> deleteOfLime(@PathVariable(name = "id") Long id){
        producerService.deleteOfLime(id);
        return ResponseEntity.ok().build();
    }

}
