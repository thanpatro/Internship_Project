package com.example.demo.directory.repository;

import com.example.demo.directory.entity.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device, String> {

    List<Device> findByEmployeeId(String employeeId);

}
