package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.dto.OrderStatusTrendDTO;
import com.classicmodels.classicmodels.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(String status);

    List<Order> findByCustomerNumber_Id(Integer customerNumber);

    List<Order> findByStatusAndCustomerNumber_Id(String status, Integer customerNumber);

    @Query("SELECT o.status AS status, o.orderDate AS orderDate, COUNT(o) AS count " +
            "FROM Order o GROUP BY o.status, o.orderDate ORDER BY o.orderDate ASC")
    List<OrderStatusTrendDTO> getOrderStatusTrends();



//    @org.springframework.data.jpa.repository.Query(value = "SELECT COUNT(*) FROM orders WHERE YEARWEEK(createdAt, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
//    long countCreatedThisWeek();
//
//    @org.springframework.data.jpa.repository.Query(value = "SELECT COUNT(*) FROM orders WHERE YEARWEEK(createdAt, 1) = YEARWEEK(CURDATE(), 1) - 1", nativeQuery = true)
//    long countCreatedLastWeek();
}
