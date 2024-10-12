package com.micropos.service;

import com.micropos.db.ProductRepository;
import com.micropos.pojo.Category;
import com.micropos.pojo.Product;
import com.micropos.pojo.SettingsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> allProducts() {
        return productRepository.getProducts();
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Mono<Product> updateProductQuantity(String id, int quantity) {
        return productRepository.updateProductQuantity(id, quantity);
    }


    @Override
    public SettingsWrapper getSettings() {
        return productRepository.getSettings();
    }

    @Override
    public List<Category> getCategories() {
        return productRepository.getCategories();
    }

    @Override
    public Flux<Product> searchProductsByName(String word) {
        return productRepository.getProductsByName(word);
    }
}
