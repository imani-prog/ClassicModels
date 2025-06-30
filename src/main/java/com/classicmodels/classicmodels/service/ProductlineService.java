package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Productline;
import java.util.List;
import java.util.Optional;

public interface ProductlineService {
    Productline createProductline(Productline productline);
    Optional<Productline> getProductlineById(String productLine);
    List<Productline> getAllProductlines();
    Productline updateProductline(String productLine, Productline productline);
    void deleteProductline(String productLine);
}
