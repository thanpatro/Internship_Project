package com.example.demo.directory.service;
import com.example.demo.directory.entity.Device;
import com.example.demo.directory.entity.Employee;
import com.example.demo.directory.repository.DeviceRepository;
import com.example.demo.directory.repository.EmployeeRepository;
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
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void saveEmployee(){
        Employee employee = new Employee();
        employee.setName("Thanos");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("Thanos", savedEmployee.getName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void returnAllEmployees(){
        Employee thanos = new Employee("Thanos", "thanos2003@gmail.com", "1");
        Employee nikos = new Employee("Nikos", "nikos2003@gmail.com", "2");

        when(employeeRepository.findAll()).thenReturn(List.of(thanos,nikos));
        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        assertEquals("Thanos", employees.get(0).getName());
        assertEquals("Nikos", employees.get(1).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void returnEmployeeById(){
        String id="1";
        Employee employee = new Employee();
        employee.setId(id);

        when(employeeRepository.findById(id)).thenReturn(java.util.Optional.of(employee));

        Employee employeeFounded = employeeService.getEmployeeById(id);

        assertNotNull(employeeFounded);
        assertEquals(id, employeeFounded.getId());
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void updateEmployeeById(){
        String id="1";
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Old Name");

        Employee employeeUpdated = new Employee();
        employeeUpdated.setId(id);
        employeeUpdated.setName("New Name");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeUpdated);

        Employee employeeSaved = employeeService.saveEmployee(employeeUpdated);

        assertNotNull(employeeSaved);
        assertEquals("New Name", employeeSaved.getName());
        assertEquals("1", employeeSaved.getId());
        verify(employeeRepository, times(1)).save(employeeUpdated);
    }

    @Test
    void deleteEmployeeById(){
        String employeeId="e1";
        String deviceId = "d1";

        Employee employee = new Employee();
        employee.setId(employeeId);

        Device device = new Device();
        device.setSerialNumber(deviceId);

        when(deviceRepository.findByEmployeeId(employeeId)).thenReturn(List.of(device));

        employeeService.deleteEmployeeById(employeeId);

        verify(deviceRepository).deleteAll(List.of(device));
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
