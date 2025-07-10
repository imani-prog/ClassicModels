package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Order;
import com.classicmodels.classicmodels.repository.OrderRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        if (order.getId() == null || order.getId().isEmpty()) {
            order.setId(generateOrderId());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getId() == null || order.getId().isEmpty()) {
            order.setId(generateOrderId());
        }
        return orderRepository.save(order);
    }

    private String generateOrderId() {
        try {
            // Get current year
            int currentYear = java.time.LocalDate.now().getYear();

            // Count orders for current year
            String yearPrefix = "ORD-" + currentYear + "-";
            long countForYear = orderRepository.countByIdStartingWith(yearPrefix);

            // Generate sequential number (starting from 1)
            int nextNumber = (int) (countForYear + 1);

            // Format: ORD-2025-001, ORD-2025-002, etc.
            String orderId = String.format("ORD-%d-%03d", currentYear, nextNumber);

            // Check if this ID already exists and increment if needed
            while (orderRepository.existsById(orderId)) {
                nextNumber++;
                orderId = String.format("ORD-%d-%03d", currentYear, nextNumber);
            }

            return orderId;
        } catch (Exception e) {
            // Fallback to timestamp-based ID if there's an error
            return "ORD-" + System.currentTimeMillis();
        }
    }

    @Override
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> searchOrders(String status, Integer customerNumber) {
        if (status != null && customerNumber != null) {
            return orderRepository.findByStatusAndCustomerNumber_Id(status, customerNumber);
        } else if (status != null) {
            return orderRepository.findByStatus(status);
        } else if (customerNumber != null) {
            return orderRepository.findByCustomerNumber_Id(customerNumber);
        } else {
            return orderRepository.findAll();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(String id, Order order) {
        if (!orderRepository.existsById(id)) {
            return null;
        }
        order.setId(id);
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(String id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }
}
