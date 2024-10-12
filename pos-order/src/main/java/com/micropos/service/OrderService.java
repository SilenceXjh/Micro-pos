package com.micropos.service;

import com.micropos.pojo.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {

    public Mono<Order> saveOrder(Order order);

    public Flux<Order> allOrders();

    public Mono<Order> getOrderById(int id);
}
