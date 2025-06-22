package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(String status);

    List<Order> findByCustomerNumber_Id(Integer customerNumber);

    List<Order> findByStatusAndCustomerNumber_Id(String status, Integer customerNumber);
}
