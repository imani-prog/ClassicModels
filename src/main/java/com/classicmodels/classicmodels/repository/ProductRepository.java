package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByProductLine_ProductLineIgnoreCase(String productLine);
    List<Product> findByProductVendorContainingIgnoreCase(String productVendor);
    List<Product> findByProductNameContainingIgnoreCaseAndProductLine_ProductLineIgnoreCaseAndProductVendorContainingIgnoreCase(
        String productName, String productLine, String productVendor);
}
