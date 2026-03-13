package com.example.demo.directory.repository;

import com.example.demo.directory.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    List<Employee> findByCompanyId(String companyId);

}
