package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer getCustomerById(Integer id);

    Customer updateCustomer(Integer id, Customer customerDetails);

    void deleteCustomerById(Integer id);

    List<Customer> getAllCustomers();
}
