package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT MAX(CAST(SUBSTRING(e.extension, 2) AS int)) FROM Employee e WHERE e.extension LIKE 'x%'")
    Integer findMaxExtensionNumber();
}
