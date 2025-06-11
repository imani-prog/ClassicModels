package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class CustomerServiceImplementation  implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setId(generateCustomerNumber());
        return customerRepository.save(customer);

    }

    private Integer generateCustomerNumber() {
        Integer customerNumber = (int) (System.currentTimeMillis() % 1000000);
        log.info("\nGenerated customer number: {}", customerNumber);
        return customerNumber;
    }
}
