package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.entities.Productline;
import com.classicmodels.classicmodels.service.ProductlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productlines")
public class ProductlineController {

    @Autowired
    private ProductlineService productlineService;

    @PostMapping
    public ResponseEntity<Productline> createProductline(@RequestBody Productline productline) {
        try {
            // Validate that productLine is not null or empty
            if (productline.getProductLine() == null || productline.getProductLine().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Productline savedProductline = productlineService.createProductline(productline);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductline);
        } catch (DataIntegrityViolationException e) {
            // Handle duplicate key or other database constraints
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            // Log the actual error for debugging
            System.err.println("Error creating productline: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{productLine}")
    public ResponseEntity<Productline> getProductlineById(@PathVariable String productLine) {
        return productlineService.getProductlineById(productLine)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Productline>> getAllProductlines() {
        try {
            List<Productline> productlines = productlineService.getAllProductlines();
            System.out.println("Found " + productlines.size() + " productlines in database:");
            for (Productline pl : productlines) {
                System.out.println("- '" + pl.getProductLine() + "'");
            }
            return ResponseEntity.ok(productlines);
        } catch (Exception e) {
            System.err.println("Error getting productlines: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{productLine}")
    public ResponseEntity<Productline> updateProductline(@PathVariable String productLine, @RequestBody Productline productline) {
        try {
            return ResponseEntity.ok(productlineService.updateProductline(productLine, productline));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{productLine}")
    public ResponseEntity<Void> deleteProductline(@PathVariable String productLine) {
        productlineService.deleteProductline(productLine);
        return ResponseEntity.noContent().build();
    }
}
