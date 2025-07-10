package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.entities.Product;
import com.classicmodels.classicmodels.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PostMapping("/saveBatch")
    public ResponseEntity<List<Product>> saveProducts(@RequestBody List<Product> products) {
        log.info("Received {} products for batch save", products.size());

        // Log details of each product for debugging
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            log.info("Product {}: code={}, name={}, quantityInStock={}, productLine={}",
                i + 1,
                product.getProductCode(),
                product.getProductName(),
                product.getQuantityInStock(),
                product.getProductLine() != null ? product.getProductLine().getProductLine() : "null"
            );
        }

        List<Product> savedProducts = productService.saveProducts(products);
        return ResponseEntity.ok(savedProducts);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
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

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
