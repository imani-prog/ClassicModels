package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Productline;
import com.classicmodels.classicmodels.repository.ProductRepository;
import com.classicmodels.classicmodels.repository.ProductlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductlineServiceImplementation implements ProductlineService {

    @Autowired
    private ProductlineRepository productlineRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Productline createProductline(Productline productline) {
        try {
            System.out.println("Attempting to save productline: " + productline.getProductLine());

            // Validate HTML description length (adjust limit based on your database column)
            if (productline.getHtmlDescription() != null && productline.getHtmlDescription().length() > 500) {
                throw new RuntimeException("HTML description too long. Maximum 500 characters allowed.");
            }

            Productline saved = productlineRepository.save(productline);
            System.out.println("Successfully saved productline: " + saved.getProductLine());
            return saved;
        } catch (Exception e) {
            System.err.println("Error in service layer: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Productline> getProductlineById(String productLine) {
        return productlineRepository.findById(productLine);
    }

    @Override
    public List<Productline> getAllProductlines() {
        return productlineRepository.findAll();
    }

    @Override
    public Productline updateProductline(String productLine, Productline productline) {
        if (!productlineRepository.existsById(productLine)) {
            throw new RuntimeException("Productline not found");
        }
        productline.setProductLine(productLine);
        return productlineRepository.save(productline);
    }

    @Override
    public void deleteProductline(String productLine) {
        // Prevent deletion if any products reference this productline
        if (!productRepository.findByProductLine_ProductLineIgnoreCase(productLine).isEmpty()) {
            throw new RuntimeException("Cannot delete: Productline is referenced by existing products.");
        }
        productlineRepository.deleteById(productLine);
    }
}
