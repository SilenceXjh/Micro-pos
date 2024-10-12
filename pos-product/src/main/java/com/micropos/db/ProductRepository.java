package com.micropos.db;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.micropos.pojo.Category;
import com.micropos.pojo.Product;
import com.micropos.pojo.SettingsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private List<Product> products;
    private SettingsWrapper settingsWrapper;
    private List<Category> categories;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        loadDataFromJsonFile();
        loadProductsFromDB();
    }

    private void loadDataFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("settings.json");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {
            settingsWrapper = mapper.readValue(reader, SettingsWrapper.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("categories.json");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {
            categories = mapper.readValue(reader, new TypeReference<List<Category>>() {});
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadProductsFromDB() {
        String sql = "(select * from product where main_category = 'All Beauty' and price <> 0 limit 20)" +
                "  Union all " +
                "(select * from product where main_category = 'Digital Music' and price <> 0 limit 20)";
        this.products = jdbcTemplate.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getString("parent_asin"));
                product.setPrice(rs.getFloat("price"));
                product.setCategory(rs.getString("main_category"));
                product.setQuantity(20);
                product.setName(rs.getString("title"));
                product.setStock(1);
                product.setImg(rs.getString("image"));
                product.setAverageRating(rs.getFloat("average_rating"));
                product.setRatingNumber(rs.getInt("rating_number"));
                product.setDescription(rs.getString("description"));
                product.setStore(rs.getString("store"));
                product.setCategories(rs.getString("categories"));
                return product;
            }
        });
    }

    public Flux<Product> getProducts() {
        return Flux.fromIterable(products);
    }

    public Mono<Product> getProductById(String id) {
        for (Product product : products) {
            if(product.getId().equals(id)) {
                return Mono.just(product);
            }
        }
        return Mono.empty();
    }

    public Mono<Product> updateProductQuantity(String id, int quantity) {
        for (Product product : products) {
            if(product.getId().equals(id)) {
                product.setQuantity(quantity);
                return Mono.just(product);
            }
        }
        return Mono.empty();
    }

    public Flux<Product> getProductsByName(String word) {
        List<Product> res = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(word)) {
                res.add(product);
            }
        }
        return Flux.fromIterable(res);
    }

    public SettingsWrapper getSettings() {
        return settingsWrapper;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
