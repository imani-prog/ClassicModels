package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Productline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductlineRepository extends JpaRepository<Productline, String> {
}
