package com.classicmodels.classicmodels.controllers;


import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String name) {
//        List<Customer> customers = customerService.searchByName(name);
//        return ResponseEntity.ok(customers);
//    }


//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
//        customerService.deleteCustomerById(id);
//        return ResponseEntity.noContent().build();
//    }
}


