package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Order createOrder(Order order);
    Optional<Order> getOrderById(Integer id);
    List<Order> searchOrders(String status, Integer customerNumber);
    List<Order> getAllOrders();
    Order updateOrder(Integer id, Order order);
    boolean deleteOrder(Integer id);
}
