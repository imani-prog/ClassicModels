package com.classicmodels.classicmodels.controllers;


import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(new Customer());
    }

}
