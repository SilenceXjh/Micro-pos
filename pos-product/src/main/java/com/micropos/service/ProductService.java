package com.micropos.service;

import com.micropos.pojo.Category;
import com.micropos.pojo.SettingsWrapper;
import com.micropos.pojo.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {

    public Flux<Product> allProducts();

    public Mono<Product> getProductById(String id);

    public Mono<Product> updateProductQuantity(String id, int quantity);

    public SettingsWrapper getSettings();

    public List<Category> getCategories();

    public Flux<Product> searchProductsByName(String word);
}
