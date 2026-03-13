package com.example.demo.directory.service;

import com.example.demo.directory.entity.Device;
import com.example.demo.directory.entity.Employee;
import com.example.demo.directory.repository.CompanyRepository;
import com.example.demo.directory.repository.DeviceRepository;
import com.example.demo.directory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id){
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteEmployeeById(String id) {
        List<Device> devices = deviceRepository.findByEmployeeId(id);
        deviceRepository.deleteAll(devices);
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployeeById(String id, Employee incomingEmployee){
       return employeeRepository.findById(id).map(existing -> {

           if (incomingEmployee.getName() != null) {
               existing.setName(incomingEmployee.getName());
           }
           if (incomingEmployee.getEmail() != null){
               existing.setEmail(incomingEmployee.getEmail());
           }
           if (incomingEmployee.getCompanyId() != null) {
               existing.setCompanyId(incomingEmployee.getCompanyId());
           }

           return employeeRepository.save(existing);
       } ).orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}

