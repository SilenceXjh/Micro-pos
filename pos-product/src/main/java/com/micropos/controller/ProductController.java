package com.micropos.controller;

import com.micropos.pojo.Product;
import com.micropos.pojo.Quantity;
import com.micropos.pojo.SettingsWrapper;
import com.micropos.pojo.Category;
import com.micropos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:63342")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setPosService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products",
            produces = { "application/json" })
    public Mono<ResponseEntity<Flux<Product>>> listProducts() {
        Flux<Product> products = productService.allProducts();
        return Mono.just(new ResponseEntity<>(products, HttpStatus.OK));
    }

    @GetMapping(value = "/products/{productId}",
            produces = { "application/json" })
    public Mono<ResponseEntity<Product>> showProductById(@PathVariable("productId") String productId) {
        return productService.getProductById(productId).flatMap(p -> Mono.just(new ResponseEntity<>(p, HttpStatus.OK)));
    }

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/products/{productId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Mono<ResponseEntity<Product>> updateProductQuantity(@PathVariable("productId") String productId, @RequestBody Quantity quantity) {
        return productService.updateProductQuantity(productId, quantity.getQuantity()).flatMap(p -> Mono.just(new ResponseEntity<>(p, HttpStatus.OK)));
    }

    @GetMapping("/search/{word}")
    public Mono<ResponseEntity<Flux<Product>>> searchProductsByName(@PathVariable String word) {
        Flux<Product> products = productService.searchProductsByName(word);
        return Mono.just(new ResponseEntity<>(products, HttpStatus.OK));
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsWrapper> getSettings() {
        return new ResponseEntity<>(productService.getSettings(), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(productService.getCategories(), HttpStatus.OK);
    }
}
