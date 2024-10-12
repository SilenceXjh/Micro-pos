package com.micropos.controller;

import com.micropos.pojo.Order;
import com.micropos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:63342")
public class OrderController {

    private OrderService orderService;

    private StreamBridge streamBridge;

    private WebClient webClient = WebClient.builder().build();

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/orders",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Mono<ResponseEntity<Order>> saveOrder(@RequestBody Order order) {
        // System.out.println(order);
        // streamBridge.send("orderOutput", order);
        Mono<String> stringMono = webClient.post()
                .uri("http://localhost:4444/deliveries")
                .body(Mono.just(order), Order.class)
                .retrieve()
                .bodyToMono(String.class);
        System.out.println(stringMono.block());
        return orderService.saveOrder(order).flatMap(o -> {
            // streamBridge.send("orderOutput", o);
            return Mono.just(new ResponseEntity<>(o, HttpStatus.OK));
        });
    }

    @GetMapping(value = "/orders", produces = { "application/json" })
    public Mono<ResponseEntity<Flux<Order>>> listAllOrders() {
        Flux<Order> orders = orderService.allOrders();
        return Mono.just(new ResponseEntity<>(orders, HttpStatus.OK));
    }

    @GetMapping(value = "/orders/{orderId}", produces = { "application/json" })
    public Mono<ResponseEntity<Order>> showOrderById(@PathVariable("orderId") Integer orderId) {
        return orderService.getOrderById(orderId).flatMap(o -> Mono.just(new ResponseEntity<>(o, HttpStatus.OK)));
    }
}
