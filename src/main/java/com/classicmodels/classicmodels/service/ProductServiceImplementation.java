package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Product;
import com.classicmodels.classicmodels.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
// This class is a placeholder for the CustomerService implementation.
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        product.setId(generateProductCode());
        return productRepository.save(product);
    }
private Integer generateProductCode(){
    Integer productCode = (int) (System.currentTimeMillis() % 1000000);
    log.info("\nGenerated product code: {}", productCode);
    return productCode;
}
}
