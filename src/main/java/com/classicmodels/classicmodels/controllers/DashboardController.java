package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProductlineRepository productlineRepository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        long productCount = productRepository.count();
        long customerCount = customerRepository.count();
        long orderCount = orderRepository.count();

        stats.put("products", productCount);
        stats.put("productTrend", 5);
        stats.put("customers", customerCount);
        stats.put("customerTrend", -2);
        stats.put("orders", orderCount);
        stats.put("orderTrend", 3);
        return stats;
    }

    @GetMapping("/entity-distribution")
    public List<Map<String, Object>> getEntityDistribution() {
        List<Map<String, Object>> data = new ArrayList<>();
        long productCount = productRepository.count();
        long customerCount = customerRepository.count();
        long orderCount = orderRepository.count();
        long employeeCount = employeeRepository.count();
        long officeCount = officeRepository.count();
        long paymentCount = paymentRepository.count();
        long productlineCount = productlineRepository.count();
        data.add(Map.of("name", "Products", "value", productCount));
        data.add(Map.of("name", "Customers", "value", customerCount));
        data.add(Map.of("name", "Orders", "value", orderCount));
        data.add(Map.of("name", "Employees", "value", employeeCount));
        data.add(Map.of("name", "Offices", "value", officeCount));
        data.add(Map.of("name", "Payments", "value", paymentCount));
        data.add(Map.of("name", "Productlines", "value", productlineCount));
        return data;
    }

    @GetMapping("/order-trend")
    public List<Map<String, Object>> getOrderTrend() {
        List<Map<String, Object>> data = new ArrayList<>();
        data.add(Map.of("day", "Mon", "orders", 15));
        data.add(Map.of("day", "Tue", "orders", 18));
        data.add(Map.of("day", "Wed", "orders", 20));
        data.add(Map.of("day", "Thu", "orders", 22));
        data.add(Map.of("day", "Fri", "orders", 25));
        data.add(Map.of("day", "Sat", "orders", 10));
        data.add(Map.of("day", "Sun", "orders", 10));
        return data;
    }

    @GetMapping("/notifications")
    public List<String> getNotifications() {
        return List.of(
            "New order received from customer #123.",
            "Product stock for 'Classic Car' is low.",
            "Customer Jane Doe registered.",
            "Order #456 has been shipped."
        );
    }
}
