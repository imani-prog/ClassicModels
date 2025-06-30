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

    @PostMapping("/save")
    public ResponseEntity<Productline> saveProductline(@RequestBody Productline productline) {
        try {
            // Validate that productLine is not null or empty
            if (productline.getProductLine() == null || productline.getProductLine().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Productline savedProductline = productlineService.createProductline(productline);
            return ResponseEntity.ok(savedProductline);
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

    @PostMapping("/saveBatch")
    public ResponseEntity<List<Productline>> saveProductlines(@RequestBody List<Productline> productlines) {
        try {
            List<Productline> savedProductlines = productlines.stream()
                    .map(productlineService::createProductline)
                    .toList();
            return ResponseEntity.ok(savedProductlines);
        } catch (Exception e) {
            System.err.println("Error saving batch productlines: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productline> getProductlineById(@PathVariable String id) {
        Productline productline = productlineService.getProductlineById(id).orElse(null);
        if (productline != null) {
            return ResponseEntity.ok(productline);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductline(@PathVariable String id) {
        Productline productline = productlineService.getProductlineById(id).orElse(null);
        if (productline != null) {
            try {
                productlineService.deleteProductline(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productline> updateProductline(@PathVariable String id, @RequestBody Productline productlineDetails) {
        try {
            Productline updated = productlineService.updateProductline(id, productlineDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Productline>> getAllProductlines() {
        List<Productline> productlines = productlineService.getAllProductlines();
        return ResponseEntity.ok(productlines);
    }
}


@RequestMapping("/productlines")
class ProductlineFrontendController {

    @Autowired
    private ProductlineService productlineService;

    @GetMapping
    public ResponseEntity<List<Productline>> getAllProductlines() {
        try {
            List<Productline> productlines = productlineService.getAllProductlines();
            return ResponseEntity.ok(productlines);
        } catch (Exception e) {
            System.err.println("Error getting productlines: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{productLine}")
    public ResponseEntity<Productline> getProductlineById(@PathVariable String productLine) {
        return productlineService.getProductlineById(productLine)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
