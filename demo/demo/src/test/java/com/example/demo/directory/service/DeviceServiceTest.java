package com.example.demo.directory.service;
import com.example.demo.directory.entity.Device;
import com.example.demo.directory.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {
    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void saveDevice(){
        Device device = new Device();
        device.setName("Laptop Dell");

        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        Device savedDevice = deviceService.saveDevice(device);

        assertNotNull(savedDevice);
        assertEquals("Laptop Dell", savedDevice.getName());
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void returnAllDevices(){
        Device laptop1 = new Device("Laptop Dell", "Laptop", "1", "a");
        Device laptop2 = new Device("Laptop HP", "laptop", "2", "b");

        when(deviceRepository.findAll()).thenReturn(List.of(laptop1,laptop2));
        List<Device> devices = deviceService.getAllDevices();

        assertEquals(2, devices.size());
        assertEquals("Laptop Dell", devices.get(0).getName());
        assertEquals("Laptop HP", devices.get(1).getName());
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    void returnDeviceById(){
        String id="1";
        Device device = new Device();
        device.setSerialNumber(id);

        when(deviceRepository.findById(id)).thenReturn(java.util.Optional.of(device));

        Device deviceFounded = deviceService.getDeviceById(id);

        assertNotNull(deviceFounded);
        assertEquals(id, deviceFounded.getSerialNumber());
        verify(deviceRepository, times(1)).findById(id);
    }

    @Test
    void updateDeviceById(){
        String id="1";
        Device device = new Device();
        device.setSerialNumber(id);
        device.setName("Old Name");

        Device deviceUpdated = new Device();
        deviceUpdated.setSerialNumber(id);
        deviceUpdated.setName("New Name");

        when(deviceRepository.save(any(Device.class))).thenReturn(deviceUpdated);

        Device deviceSaved = deviceService.saveDevice(deviceUpdated);

        assertNotNull(deviceSaved);
        assertEquals("New Name",deviceSaved.getName());
        assertEquals("1", deviceSaved.getSerialNumber());
        verify(deviceRepository, times(1)).save(deviceUpdated);
    }

    @Test
    void deleteDeviceById(){
        String deviceId = "d1";

        Device device = new Device();
        device.setSerialNumber(deviceId);

        deviceService.deleteDeviceById(deviceId);

        verify(deviceRepository, times(1)).deleteById(deviceId);
    }
}
