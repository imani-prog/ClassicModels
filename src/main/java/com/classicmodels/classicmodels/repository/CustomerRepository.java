package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//    @org.springframework.data.jpa.repository.Query(value = "SELECT COUNT(*) FROM customers WHERE YEARWEEK(createdAt, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
//    long countCreatedThisWeek();
//
//    @org.springframework.data.jpa.repository.Query(value = "SELECT COUNT(*) FROM customers WHERE YEARWEEK(createdAt, 1) = YEARWEEK(CURDATE(), 1) - 1", nativeQuery = true)
//    long countCreatedLastWeek();

}
