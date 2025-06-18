package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.entities.Product;
import com.classicmodels.classicmodels.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(new Product());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(required = false) String productName,
                                                       @RequestParam(required = false) String productLine,
                                                       @RequestParam(required = false) String productVendor) {
        List<Product> products = productService.searchProducts(productName, productLine, productVendor);
        return ResponseEntity.ok(products);
    }

}
