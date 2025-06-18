package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class CustomerServiceImplementation  implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(generateCustomerNumber());
        }
        return customerRepository.save(customer);

    }

    private Integer generateCustomerNumber() {
        Integer customerNumber = (int) (System.currentTimeMillis() % 1000000);
        log.info("\nGenerated customer number: {}", customerNumber);
        return customerNumber;
    }

    @Override
    public Customer getCustomerById(Integer id) {
        log.info("Fetching customer with ID: {}", id);
        return customerRepository.findById(Integer.valueOf(id)).orElse(null);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        log.info("Deleting customer with ID: {}", id);
        customerRepository.deleteById(id);
    }


    @Override
    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // Update only allowed fields (NOT the ID)
        existingCustomer.setCustomerName(customerDetails.getCustomerName());
        existingCustomer.setContactFirstName(customerDetails.getContactFirstName());
        existingCustomer.setContactLastName(customerDetails.getContactLastName());
        existingCustomer.setPhone(customerDetails.getPhone());
        existingCustomer.setAddressLine1(customerDetails.getAddressLine1());
        existingCustomer.setAddressLine2(customerDetails.getAddressLine2());
        existingCustomer.setCity(customerDetails.getCity());
        existingCustomer.setState(customerDetails.getState());
        existingCustomer.setPostalCode(customerDetails.getPostalCode());
        existingCustomer.setCountry(customerDetails.getCountry());
        existingCustomer.setCreditLimit(customerDetails.getCreditLimit());
        existingCustomer.setSalesRepEmployeeNumber(customerDetails.getSalesRepEmployeeNumber());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }



}
