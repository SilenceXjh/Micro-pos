package com.micropos.pojo;

import lombok.Data;

@Data
public class Delivery {

    private int id;

    private Order order;

    DeliveryStatus status = DeliveryStatus.PREPARING;
}
