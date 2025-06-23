package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
