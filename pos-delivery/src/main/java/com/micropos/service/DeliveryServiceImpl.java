package com.micropos.service;

import com.micropos.db.DeliveryRepository;
import com.micropos.pojo.Delivery;
import com.micropos.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    @Autowired
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Flux<Delivery> allDeliveries() {
        return deliveryRepository.allDeliveries();
    }

    @Override
    public Mono<Delivery> createDelivery(Order order) {
        return deliveryRepository.createDelivery(order);
    }

    @Override
    public Mono<Delivery> getDeliveryById(int deliveryId) {
        return deliveryRepository.getDeliveryById(deliveryId);
    }
}
