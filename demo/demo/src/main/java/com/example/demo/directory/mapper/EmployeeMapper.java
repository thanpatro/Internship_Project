package com.example.demo.directory.mapper;

import com.example.demo.directory.dto.EmployeeDto;
import com.example.demo.directory.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDto convertToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setCompanyId(employee.getCompanyId());

        return employeeDto;
    }

    public Employee convertToEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setCompanyId(employeeDto.getCompanyId());

        return employee;
    }
}
