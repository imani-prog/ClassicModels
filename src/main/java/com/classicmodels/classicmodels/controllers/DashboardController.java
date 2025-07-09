package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.dto.OrderStatusTrendDTO;
import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@Slf4j
public class DashboardController {

    @Autowired private ProductRepository productRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private OfficeRepository officeRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private ProductlineRepository productlineRepository;


    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        log.info("Dashboard stats request received");
        Map<String, Object> stats = new HashMap<>();

        try {
            long productCount = productRepository.count();
            long customerCount = customerRepository.count();
            long orderCount = orderRepository.count();

            log.info("Raw counts - Products: {}, Customers: {}, Orders: {}", productCount, customerCount, orderCount);

            stats.put("products", productCount);
            stats.put("productTrend", 5);
            stats.put("customers", customerCount);
            stats.put("customerTrend", -2);
            stats.put("orders", orderCount);
            stats.put("orderTrend", 3);

            log.info("Stats response: {}", stats);
            return stats;
        } catch (Exception e) {
            log.error("Error fetching stats: {}", e.getMessage());
            // Return default values if there's an error
            stats.put("products", 0);
            stats.put("productTrend", 0);
            stats.put("customers", 0);
            stats.put("customerTrend", 0);
            stats.put("orders", 0);
            stats.put("orderTrend", 0);
            return stats;
        }
    }

    @GetMapping("/entity-distribution")
    public List<Map<String, Object>> getEntityDistribution() {
        log.info("Entity distribution request received");
        List<Map<String, Object>> data = new ArrayList<>();

        try {
            long productCount = productRepository.count();
            long customerCount = customerRepository.count();
            long orderCount = orderRepository.count();
            long employeeCount = employeeRepository.count();
            long officeCount = officeRepository.count();
            long paymentCount = paymentRepository.count();
            long productlineCount = productlineRepository.count();

            log.info("Entity counts - Products: {}, Customers: {}, Orders: {}, Employees: {}, Offices: {}, Payments: {}, Productlines: {}",
                    productCount, customerCount, orderCount, employeeCount, officeCount, paymentCount, productlineCount);

            data.add(Map.of("name", "Products", "value", productCount));
            data.add(Map.of("name", "Customers", "value", customerCount));
            data.add(Map.of("name", "Orders", "value", orderCount));
            data.add(Map.of("name", "Employees", "value", employeeCount));
            data.add(Map.of("name", "Offices", "value", officeCount));
            data.add(Map.of("name", "Payments", "value", paymentCount));
            data.add(Map.of("name", "Productlines", "value", productlineCount));

            log.info("Entity distribution response: {}", data);
            return data;
        } catch (Exception e) {
            log.error("Error fetching entity distribution: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/notifications")
    public List<String> getNotifications() {
        return List.of("Customer #788 deleted", "Order #102 saved", "Employee #55 updated");
    }

    @GetMapping("/top-payments")
    public List<Map<String, Object>> getTopPayments() {
        List<Payment> topPayments = paymentRepository.findTop10ByOrderByAmountDesc();
        List<Map<String, Object>> results = new ArrayList<>();

        for (Payment payment : topPayments) {
            Map<String, Object> p = new HashMap<>();
            p.put("customerNumber", payment.getCustomer() != null ? payment.getCustomer().getCustomerNumber() : null);
            p.put("checkNumber", payment.getId().getCheckNumber());
            p.put("amount", payment.getAmount());
            results.add(p);
        }

        return results;
    }

    @GetMapping("/revenue-summary")
    public Map<String, Object> getRevenueSummary() {
        double totalRevenue = paymentRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getAmount().doubleValue())
                .sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRevenue", totalRevenue);
        summary.put("trend", 8); // You can calculate this properly if you track per-month revenue
        return summary;
    }

    @GetMapping("/top-products")
    public List<Map<String, Object>> getTopProducts() {
        Pageable topFive = PageRequest.of(0, 5);
        return productRepository.findTop5ByBuyPriceDesc(topFive);
    }
    @GetMapping("/order-status-trend")
    public List<Map<String, Object>> getOrderStatusTrend() {
        List<OrderStatusTrendDTO> rawData = orderRepository.getOrderStatusTrends();

        // Use a sorted map to keep dates in order
        Map<LocalDate, Map<String, Object>> trendMap = new TreeMap<>();

        for (OrderStatusTrendDTO dto : rawData) {
            LocalDate date = dto.getOrderDate();
            String status = dto.getStatus().toLowerCase();
            Long count = dto.getCount();

            // Initialize map for date if it doesn't exist
            trendMap.putIfAbsent(date, new LinkedHashMap<>());
            Map<String, Object> dailyData = trendMap.get(date);

            // Always set the date key
            dailyData.put("date", date.toString());

            // Set the specific status count
            dailyData.put(status, count);
        }

        // Normalize data to include all statuses with 0 if missing
        for (Map<String, Object> dayData : trendMap.values()) {
            dayData.putIfAbsent("shipped", 0L);
            dayData.putIfAbsent("completed", 0L);
            dayData.putIfAbsent("pending", 0L);
        }

        return new ArrayList<>(trendMap.values());
    }

    // Test endpoint to check if dashboard is accessible
    @GetMapping("/test")
    public ResponseEntity<?> testDashboard() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Dashboard endpoint is working");
        response.put("timestamp", System.currentTimeMillis());
        response.put("server", "Spring Boot Backend");

        // Test database connectivity
        try {
            long productCount = productRepository.count();
            response.put("databaseConnected", true);
            response.put("sampleProductCount", productCount);
        } catch (Exception e) {
            response.put("databaseConnected", false);
            response.put("databaseError", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
