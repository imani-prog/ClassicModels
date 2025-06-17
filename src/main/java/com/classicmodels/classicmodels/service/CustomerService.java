package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer getCustomerById(Integer id);

//    void deleteCustomerById(Integer id);
}
