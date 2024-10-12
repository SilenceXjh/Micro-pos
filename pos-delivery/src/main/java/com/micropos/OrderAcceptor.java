package com.micropos;

import com.micropos.pojo.Order;
import com.micropos.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderAcceptor implements Consumer<Order> {

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public void accept(Order order) {
        deliveryService.createDelivery(order);
        System.out.println("a delivery created");
    }
}
