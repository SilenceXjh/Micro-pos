package com.micropos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;

    private float price;

    // @Column(name = "main_category")
    private String category;

    private int quantity;

    // @Column(name = "title")
    private String name;

    private int stock;

    //@Column(name = "image")
    private String img;

    //@Column(name = "average_rating")
    private float averageRating;

    //@Column(name = "rating_number")
    private int ratingNumber;

    private String description;

    private String store;

    private String categories;

}