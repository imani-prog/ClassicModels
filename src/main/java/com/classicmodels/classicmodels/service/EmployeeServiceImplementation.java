package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.EmployeeDto;
import com.classicmodels.classicmodels.entities.Employee;
import com.classicmodels.classicmodels.entities.Office;
import com.classicmodels.classicmodels.repository.EmployeeRepository;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OfficeRepository officeRepository;

    private EmployeeDto toDto(Employee employee) {
        if (employee == null) return null;
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getEmployeeNumber());
        dto.setLastName(employee.getLastName());
        dto.setFirstName(employee.getFirstName());
        dto.setExtension(employee.getExtension());
        dto.setEmail(employee.getEmail());
        dto.setOfficeCode(employee.getOfficeCode() != null ? employee.getOfficeCode().getOfficeCode() : null);
        dto.setReportsTo(employee.getReportsTo() != null ? employee.getReportsTo().getEmployeeNumber() : null);
        dto.setJobTitle(employee.getJobTitle());
        return dto;
    }

    private Employee toEntity(EmployeeDto dto) {
        if (dto == null) return null;
        Employee employee = new Employee();
        employee.setEmployeeNumber(dto.getId());
        employee.setLastName(dto.getLastName());
        employee.setFirstName(dto.getFirstName());
        employee.setExtension(dto.getExtension());
        employee.setEmail(dto.getEmail());
        if (dto.getOfficeCode() != null) {
            Office office = officeRepository.findById(dto.getOfficeCode()).orElse(null);
            employee.setOfficeCode(office);
        }
        if (dto.getReportsTo() != null) {
            Employee manager = employeeRepository.findById(dto.getReportsTo()).orElse(null);
            employee.setReportsTo(manager);
        }
        employee.setJobTitle(dto.getJobTitle());
        return employee;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // Auto-generate extension if not provided
        if (employeeDto.getExtension() == null || employeeDto.getExtension().isEmpty()) {
            Integer maxExt = employeeRepository.findMaxExtensionNumber();
            int nextExt = (maxExt != null ? maxExt + 1 : 1001);
            employeeDto.setExtension("x" + nextExt);
        }
        Employee employee = toEntity(employeeDto);
        Employee saved = employeeRepository.save(employee);
        return toDto(saved);
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(this::toDto).orElse(null);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto) {
        if (employeeRepository.existsById(id)) {
            // Auto-generate extension if not provided
            if (employeeDto.getExtension() == null || employeeDto.getExtension().isEmpty()) {
                Integer maxExt = employeeRepository.findMaxExtensionNumber();
                int nextExt = (maxExt != null ? maxExt + 1 : 1001);
                employeeDto.setExtension("x" + nextExt);
            }
            Employee employee = toEntity(employeeDto);
            employee.setEmployeeNumber(id);
            Employee updated = employeeRepository.save(employee);
            return toDto(updated);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
