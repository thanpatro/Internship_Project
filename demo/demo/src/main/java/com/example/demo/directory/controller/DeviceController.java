package com.example.demo.directory.controller;

import com.example.demo.directory.dto.DeviceDto;
import com.example.demo.directory.entity.Device;
import com.example.demo.directory.mapper.DeviceMapper;
import com.example.demo.directory.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

    public DeviceController(DeviceService deviceService) {
        this.deviceService =deviceService;
    }

    @GetMapping
    public List<DeviceDto> getAllDevices(){
        Device device = new Device();
        return deviceService.getAllDevices().stream().map(deviceMapper::convertToDeviceDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable String id){
        Device device = deviceService.getDeviceById(id);
        DeviceDto deviceDto = deviceMapper.convertToDeviceDto(device);
        return ResponseEntity.ok(deviceDto);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto) {
        Device device = deviceMapper.convertToDevice(deviceDto);
        Device deviceSaved = deviceService.saveDevice(device);
        DeviceDto deviceDtoSaved = deviceMapper.convertToDeviceDto(deviceSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDtoSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable String id, @RequestBody DeviceDto deviceDto){
        Device device = deviceMapper.convertToDevice(deviceDto);
        Device deviceUpdated = deviceService.updateDeviceById(id,device);
        DeviceDto deviceDtoUpdate = deviceMapper.convertToDeviceDto(deviceUpdated);

        return ResponseEntity.ok(deviceDtoUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id){
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

}
