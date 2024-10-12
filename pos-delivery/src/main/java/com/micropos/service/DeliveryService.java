package com.micropos.service;

import com.micropos.pojo.Delivery;
import com.micropos.pojo.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryService {

    public Flux<Delivery> allDeliveries();

    public Mono<Delivery> createDelivery(Order order);

    public Mono<Delivery> getDeliveryById(int deliveryId);
}
