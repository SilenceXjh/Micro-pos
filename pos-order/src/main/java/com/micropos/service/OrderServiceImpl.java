package com.micropos.service;

import com.micropos.db.OrderRepository;
import com.micropos.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<Order> saveOrder(Order order) {
        return orderRepository.saveOrder(order);
    }

    @Override
    public Flux<Order> allOrders() {
        return orderRepository.allOrders();
    }

    @Override
    public Mono<Order> getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }
}
