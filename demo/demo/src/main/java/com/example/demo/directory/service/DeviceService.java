package com.example.demo.directory.service;

import com.example.demo.directory.dto.CompanyDto;
import com.example.demo.directory.entity.Company;
import com.example.demo.directory.entity.Device;
import com.example.demo.directory.entity.Employee;
import com.example.demo.directory.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device saveDevice(Device device){
        return deviceRepository.save(device);
    }

    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    public Device getDeviceById(String id){
        return deviceRepository.findById(id).orElse(null);
    }

    public void deleteDeviceById(String id){
        deviceRepository.deleteById(id);
    }

    public Device updateDeviceById(String id, Device incomingDevice){
        return deviceRepository.findById(id).map(existing -> {

            if (incomingDevice.getName() != null) {
                existing.setName(incomingDevice.getName());
            }
            if (incomingDevice.getType() != null) {
                existing.setType(incomingDevice.getType());
            }
            if (incomingDevice.getEmployeeId() != null) {
                existing.setCompanyId(incomingDevice.getCompanyId());
            }

            return deviceRepository.save(existing);
        } ).orElseThrow(() -> new RuntimeException("Device not found"));
    }

}
