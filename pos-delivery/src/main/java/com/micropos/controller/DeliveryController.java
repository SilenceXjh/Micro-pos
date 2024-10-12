package com.micropos.controller;

import com.micropos.pojo.Delivery;
import com.micropos.pojo.Order;
import com.micropos.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/deliveries")
    public Mono<ResponseEntity<Flux<Delivery>>> listAllDeliveries() {
        Flux<Delivery> deliveries = deliveryService.allDeliveries();
        return Mono.just(new ResponseEntity<>(deliveries, HttpStatus.OK));
    }

    @PostMapping(value = "/deliveries")
    public Mono<ResponseEntity<String>> createDelivery(@RequestBody Order order) {
        // System.out.println(order);
        deliveryService.createDelivery(order);
        return Mono.just(new ResponseEntity<>("delivery created", HttpStatus.OK));
    }

    @GetMapping("/deliveries/{deliveryId}")
    public Mono<ResponseEntity<Delivery>> showDeliveryById(@PathVariable Integer deliveryId) {
        return deliveryService.getDeliveryById(deliveryId).flatMap(delivery ->
                Mono.just(new ResponseEntity<>(delivery, HttpStatus.OK)));
    }
}
