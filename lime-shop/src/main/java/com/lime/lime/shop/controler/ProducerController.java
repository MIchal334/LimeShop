package com.lime.lime.shop.controler;

import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
        List<ProducerOrderReadModel> result = producerService.getActuallyOrders();
        return ResponseEntity.ok(result);
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
}
