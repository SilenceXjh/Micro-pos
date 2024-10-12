package com.micropos.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Order {

    private int orderNumber;

    private List<Item> orderItems;

    private String date;

    private float subTotal;

    private float discount;

    private float total;

    private float paid;

    private float change;

    private String paymentType;
}
