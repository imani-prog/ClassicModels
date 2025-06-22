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
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
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
    public Order updateOrder(Integer id, Order order) {
        if (!orderRepository.existsById(id)) {
            return null;
        }
        order.setId(id);
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }
}
