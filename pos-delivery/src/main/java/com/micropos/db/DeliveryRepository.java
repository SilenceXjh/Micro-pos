package com.micropos.db;

import com.micropos.pojo.Delivery;
import com.micropos.pojo.DeliveryStatus;
import com.micropos.pojo.Order;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DeliveryRepository {

    private List<Delivery> deliveries = new ArrayList<>();
    private int curId = 1;

    public Mono<Delivery> createDelivery(Order order) {
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setId(curId++);
        deliveries.add(delivery);
        return Mono.just(delivery);
    }

    public Flux<Delivery> allDeliveries() {
        return Flux.fromIterable(deliveries);
    }

    public Mono<Delivery> getDeliveryById(int deliveryId) {
        for (Delivery delivery : deliveries) {
            if(delivery.getId() == deliveryId) {
                if(delivery.getStatus() == DeliveryStatus.PREPARING) {
                    delivery.setStatus(DeliveryStatus.DELIVERING);
                } else if(delivery.getStatus() == DeliveryStatus.DELIVERING) {
                    delivery.setStatus(DeliveryStatus.ARRIVED);
                }
                return Mono.just(delivery);
            }
        }
        return Mono.empty();
    }

}
