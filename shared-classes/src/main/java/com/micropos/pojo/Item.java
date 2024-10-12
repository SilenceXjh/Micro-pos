package com.micropos.pojo;

import lombok.Data;

@Data
public class Item {
    private String productName;

    private int amount;

    private float price;
}
