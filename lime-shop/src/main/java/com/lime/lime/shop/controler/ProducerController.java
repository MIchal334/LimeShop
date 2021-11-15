package com.lime.lime.shop.controler;

import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.dto.ProducerOrderReadModel;
import com.lime.lime.shop.service.ProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producer")
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
}
