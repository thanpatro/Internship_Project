package com.example.demo.directory.service;

import com.example.demo.directory.entity.Company;
import com.example.demo.directory.entity.Device;
import com.example.demo.directory.entity.Employee;
import com.example.demo.directory.repository.CompanyRepository;
import com.example.demo.directory.repository.DeviceRepository;
import com.example.demo.directory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    public Company saveCompany(Company company){
        if (company.getName() == null || company.getName().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies(){

        return companyRepository.findAll();
    }

    public Company findById(String id){
        return companyRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id){

        List<Employee> employees = employeeRepository.findByCompanyId(id);
        for (Employee employee : employees){
            List<Device> devices = deviceRepository.findByEmployeeId(employee.getId());
            deviceRepository.deleteAll(devices);

            employeeRepository.deleteById(employee.getId());
        }

        companyRepository.deleteById(id);
    }

    public Company updateCompanyById(String id, Company incomingCompany){
        return companyRepository.findById(id).map(existing -> {

            if (incomingCompany.getName() != null) {
                existing.setName(incomingCompany.getName());
            }
            if (incomingCompany.getAddress() != null) {
                existing.setAddress(incomingCompany.getAddress());
            }

            return companyRepository.save(existing);
        } ).orElseThrow(() -> new RuntimeException("Company not found"));
    }
}
