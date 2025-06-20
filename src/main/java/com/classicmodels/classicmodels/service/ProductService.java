package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    Product getProductById(String id);

    List<Product> searchProducts(String productName, String productLine, String productVendor);

    List<Product> getAllProducts();
    Product updateProduct(String id, Product product);
    boolean deleteProduct(String id);
}
