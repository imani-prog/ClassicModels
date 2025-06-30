package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByProductLine_ProductLineIgnoreCase(String productLine);
    List<Product> findByProductVendorContainingIgnoreCase(String productVendor);
    List<Product> findByProductNameContainingIgnoreCaseAndProductLine_ProductLineIgnoreCaseAndProductVendorContainingIgnoreCase(
        String productName, String productLine, String productVendor);

//    @Query(value = "SELECT COUNT(*) FROM products WHERE YEARWEEK(createdAt, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
//    long countCreatedThisWeek();
//
//    @Query(value = "SELECT COUNT(*) FROM products WHERE YEARWEEK(createdAt, 1) = YEARWEEK(CURDATE(), 1) - 1", nativeQuery = true)
//    long countCreatedLastWeek();
//

    @Query("SELECT new map(p.productName as name, p.buyPrice as price, p.quantityInStock as stock) " +
            "FROM Product p ORDER BY p.buyPrice DESC")
    List<Map<String, Object>> findTop5ByBuyPriceDesc(Pageable pageable);

}

