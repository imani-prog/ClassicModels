package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entities.Customer;
import com.classicmodels.classicmodels.entities.Employee;
import com.classicmodels.classicmodels.repository.CustomerRepository;
import com.classicmodels.classicmodels.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation  implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(generateCustomerNumber());
        }
        // Debug: print Employee before and after fetch
        if (customer.getSalesRepEmployeeNumber() != null) {
            System.out.println("[DEBUG] Incoming Employee: " + customer.getSalesRepEmployeeNumber());
            System.out.println("[DEBUG] Incoming Employee Number: " + customer.getSalesRepEmployeeNumber().getEmployeeNumber());
        }
        // Ensure salesRepEmployeeNumber is a managed entity
        if (customer.getSalesRepEmployeeNumber() != null && customer.getSalesRepEmployeeNumber().getEmployeeNumber() != null) {
            Employee managedEmployee = employeeRepository.findById(customer.getSalesRepEmployeeNumber().getEmployeeNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Employee with number " + customer.getSalesRepEmployeeNumber().getEmployeeNumber() + " not found"));
            System.out.println("[DEBUG] Managed Employee: " + managedEmployee);
            customer.setSalesRepEmployeeNumber(managedEmployee);
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
        try {
            customerRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete customer with ID " + id + ". There are related records (e.g., payments) that prevent deletion.");
        }
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

    @Override
    @Transactional
    public List<Customer> saveCustomers(List<Customer> customers) {
        int uniqueSeed = (int) (System.currentTimeMillis() % 1000000);
        int counter = 0;
        for (Customer customer : customers) {
            // Assign a unique customer number if missing
            if (customer.getId() == null) {
                customer.setId(uniqueSeed + counter);
                counter++;
            }
            // Always resolve salesRepEmployeeNumber to a managed Employee entity if present
            if (customer.getSalesRepEmployeeNumber() != null) {
                Integer empNo = customer.getSalesRepEmployeeNumber().getEmployeeNumber();
                if (empNo != null) {
                    Employee managedEmployee = employeeRepository.findById(empNo)
                        .orElseThrow(() -> new RuntimeException("Employee not found: " + empNo));
                    customer.setSalesRepEmployeeNumber(managedEmployee);
                } else {
                    customer.setSalesRepEmployeeNumber(null);
                }
            }
        }
        return customerRepository.saveAll(customers);
    }


}
