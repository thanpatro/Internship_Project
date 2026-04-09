package com.example.demo.directory.controller;

import com.example.demo.directory.mapper.EmployeeMapper;
import com.example.demo.directory.dto.EmployeeDto;
import com.example.demo.directory.entity.Employee;
import com.example.demo.directory.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees(){
        Employee employee = new Employee();
        return employeeService.getAllEmployees().stream().map(employeeMapper::convertToEmployeeDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String id) {

        Employee employee = employeeService.getEmployeeById(id);
        EmployeeDto employeeDto = employeeMapper.convertToEmployeeDto(employee);
        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        Employee employee = employeeMapper.convertToEmployee(employeeDto);
        Employee employeeSaved =  employeeService.saveEmployee(employee);
        EmployeeDto employeeDtoSaved = employeeMapper.convertToEmployeeDto(employeeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDtoSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody EmployeeDto employeeDto){
        Employee employee = employeeMapper.convertToEmployee(employeeDto);
        Employee employeeUpdated = employeeService.updateEmployeeById(id, employee);
        EmployeeDto employeeDtoUpdated = employeeMapper.convertToEmployeeDto(employeeUpdated);

        return ResponseEntity.ok(employeeDtoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

}