package com.classicmodels.classicmodels.controllers;


import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class
CustomerController {
    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

//    @PostMapping("/save")
//    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
//        Customer savedCustomer = customerService.saveCustomer(customer);
//        return ResponseEntity.ok(new Customer());
//    }

    @PostMapping("/saveBatch")
    public ResponseEntity<List<Customer>> saveCustomers(@RequestBody List<Customer> customers) {
        List<Customer> savedCustomers = customerService.saveCustomers(customers);
        return ResponseEntity.ok(savedCustomers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            try {
                customerService.deleteCustomerById(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customerDetails) {
        try {
            Customer updated = customerService.updateCustomer(id, customerDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

}