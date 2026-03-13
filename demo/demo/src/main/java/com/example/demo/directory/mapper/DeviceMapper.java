package com.example.demo.directory.mapper;

import com.example.demo.directory.dto.DeviceDto;
import com.example.demo.directory.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public DeviceDto convertToDeviceDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();

        deviceDto.setSerialNumber(device.getSerialNumber());
        deviceDto.setName(device.getName());
        deviceDto.setType(device.getType());
        deviceDto.setEmployeeId(device.getEmployeeId());

        return deviceDto;
    }

    public Device convertToDevice(DeviceDto deviceDto){
        Device device = new Device();

        device.setSerialNumber(deviceDto.getSerialNumber());
        device.setName(deviceDto.getName());
        device.setType(deviceDto.getType());
        device.setEmployeeId(deviceDto.getEmployeeId());

        return device;
    }
}
