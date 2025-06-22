package com.classicmodels.classicmodels.controllers;

import com.classicmodels.classicmodels.dto.OrderDto;
import com.classicmodels.classicmodels.dto.OrderResponseDto;
import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.entities.Order;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/save")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.customerNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + orderDto.customerNumber));
        Order order = new Order();
        order.setId(orderDto.id);
        order.setOrderDate(orderDto.orderDate);
        order.setRequiredDate(orderDto.requiredDate);
        order.setShippedDate(orderDto.shippedDate);
        order.setStatus(orderDto.status);
        order.setComments(orderDto.comments);
        order.setCustomerNumber(customer);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id)
                .map(order -> {
                    Customer customer = order.getCustomerNumber();
                    OrderResponseDto dto = new OrderResponseDto(
                        order.getId(),
                        order.getOrderDate(),
                        order.getRequiredDate(),
                        order.getShippedDate(),
                        order.getStatus(),
                        order.getComments(),
                        customer != null ? customer.getId() : null,
                        customer != null ? customer.getCustomerName() : null
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderResponseDto>> searchOrders(@RequestParam(required = false) String status,
                                                   @RequestParam(required = false) Integer customerNumber) {
        List<Order> orders = orderService.searchOrders(status, customerNumber);
        List<OrderResponseDto> dtos = orders.stream().map(order -> {
            Customer customer = order.getCustomerNumber();
            return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getRequiredDate(),
                order.getShippedDate(),
                order.getStatus(),
                order.getComments(),
                customer != null ? customer.getId() : null,
                customer != null ? customer.getCustomerName() : null
            );
        }).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDto> dtos = orders.stream().map(order -> {
            Customer customer = order.getCustomerNumber();
            return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getRequiredDate(),
                order.getShippedDate(),
                order.getStatus(),
                order.getComments(),
                customer != null ? customer.getId() : null,
                customer != null ? customer.getCustomerName() : null
            );
        }).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Integer id, @RequestBody OrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.customerNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + orderDto.customerNumber));
        Order order = new Order();
        order.setId(id);
        order.setOrderDate(orderDto.orderDate);
        order.setRequiredDate(orderDto.requiredDate);
        order.setShippedDate(orderDto.shippedDate);
        order.setStatus(orderDto.status);
        order.setComments(orderDto.comments);
        order.setCustomerNumber(customer);
        Order updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder != null) {
            OrderResponseDto dto = new OrderResponseDto(
                updatedOrder.getId(),
                updatedOrder.getOrderDate(),
                updatedOrder.getRequiredDate(),
                updatedOrder.getShippedDate(),
                updatedOrder.getStatus(),
                updatedOrder.getComments(),
                customer.getId(),
                customer.getCustomerName()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
