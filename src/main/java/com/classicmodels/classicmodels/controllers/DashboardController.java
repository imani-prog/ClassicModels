package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.dto.OrderStatusTrendDTO;
import com.classicmodels.classicmodels.entities.Payment;
import com.classicmodels.classicmodels.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
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
        data.add(Map.of("name", "Products", "value", productRepository.count()));
        data.add(Map.of("name", "Customers", "value", customerRepository.count()));
        data.add(Map.of("name", "Orders", "value", orderRepository.count()));
        data.add(Map.of("name", "Employees", "value", employeeRepository.count()));
        data.add(Map.of("name", "Offices", "value", officeRepository.count()));
        data.add(Map.of("name", "Payments", "value", paymentRepository.count()));
        data.add(Map.of("name", "Productlines", "value", productlineRepository.count()));
        return data;
    }

//    @GetMapping("/order-trend")
//    public List<Map<String, Object>> getOrderTrend() {
//        List<Map<String, Object>> data = new ArrayList<>();
//        data.add(Map.of("day", "Mon", "orders", 15));
//        data.add(Map.of("day", "Tue", "orders", 18));
//        data.add(Map.of("day", "Wed", "orders", 20));
//        data.add(Map.of("day", "Thu", "orders", 22));
//        data.add(Map.of("day", "Fri", "orders", 25));
//        data.add(Map.of("day", "Sat", "orders", 10));
//        data.add(Map.of("day", "Sun", "orders", 10));
//        return data;
//    }

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





}
