package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Product;
import com.classicmodels.classicmodels.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
// This class is a placeholder for the CustomerService implementation.
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        if (product.getProductCode() == null) {
            product.setProductCode(generateProductCode());
        }
        return productRepository.save(product);
    }
    private String generateProductCode(){
        String productCode = "P" + (System.currentTimeMillis() % 1000000);
        log.info("\nGenerated product code: {}", productCode);
        return productCode;
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> searchProducts(String productName, String productLine, String productVendor) {
        if (productName != null && !productName.isEmpty() && productLine != null && !productLine.isEmpty() && productVendor != null && !productVendor.isEmpty()) {
            return productRepository.findByProductNameContainingIgnoreCaseAndProductLine_ProductLineIgnoreCaseAndProductVendorContainingIgnoreCase(productName, productLine, productVendor);
        } else if (productName != null && !productName.isEmpty()) {
            return productRepository.findByProductNameContainingIgnoreCase(productName);
        } else if (productLine != null && !productLine.isEmpty()) {
            return productRepository.findByProductLine_ProductLineIgnoreCase(productLine);
        } else if (productVendor != null && !productVendor.isEmpty()) {
            return productRepository.findByProductVendorContainingIgnoreCase(productVendor);
        } else {
            return productRepository.findAll();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductLine(product.getProductLine());
            existingProduct.setProductScale(product.getProductScale());
            existingProduct.setProductVendor(product.getProductVendor());
            existingProduct.setProductDescription(product.getProductDescription());
            existingProduct.setQuantityInStock(product.getQuantityInStock());
            existingProduct.setBuyPrice(product.getBuyPrice());
            existingProduct.setMsrp(product.getMsrp());
            return productRepository.save(existingProduct);
        }).orElse(null);
    }

    @Override
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
