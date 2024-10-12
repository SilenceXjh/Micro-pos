package com.micropos.db;

import com.micropos.pojo.Order;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private List<Order> orders = new ArrayList<>();


    public Mono<Order> saveOrder(Order order) {
        orders.add(order);
        return Mono.just(order);
    }

    public Flux<Order> allOrders() {
        return Flux.fromIterable(orders);
    }

    public Mono<Order> getOrderById(int id) {
        for(Order order : orders) {
            if(order.getOrderNumber() == id) {
                return Mono.just(order);
            }
        }
        return Mono.empty();
    }
}
