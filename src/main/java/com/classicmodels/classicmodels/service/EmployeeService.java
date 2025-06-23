package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Integer id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto);
    void deleteEmployee(Integer id);
}
