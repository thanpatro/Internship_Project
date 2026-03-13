package com.example.demo.directory.service;
import com.example.demo.directory.entity.Company;
import com.example.demo.directory.entity.Device;
import com.example.demo.directory.entity.Employee;
import com.example.demo.directory.repository.CompanyRepository;
import com.example.demo.directory.repository.DeviceRepository;
import com.example.demo.directory.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void saveCompany(){
        Company company = new Company();
        company.setName("Microsoft");
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        Company savedCompany = companyService.saveCompany(company);

        assertNotNull(savedCompany);
        assertEquals("Microsoft", savedCompany.getName());
        verify(companyRepository, times(1)).save(company);
    }

   @Test
   void returnAllCompanies(){
        Company microsoft = new Company("Microsoft", "USA");
        Company apple = new Company("Xiaomi","China");

        when(companyRepository.findAll()).thenReturn(List.of(microsoft,apple));

        List<Company> companies = companyService.getAllCompanies();

        assertEquals(2, companies.size());
        assertEquals("Microsoft", companies.get(0).getName());
        assertEquals("Xiaomi", companies.get(1).getName());
        verify(companyRepository, times(1)).findAll();
   }

   @Test
    void returnCompanyById() {
        String id="1";
        Company company = new Company();
        company.setId(id);

        when(companyRepository.findById(id)).thenReturn(java.util.Optional.of(company));

        Company companyFounded = companyService.findById(id);

        assertNotNull(companyFounded);
        assertEquals(id, companyFounded.getId());
       verify(companyRepository, times(1)).findById(id);
   }

   @Test
    void updateCompanyById(){
        String id="1";
        Company company = new Company();
        company.setId(id);
        company.setName("Old Name");

        Company companyUpdated = new Company();
        companyUpdated.setId(id);
        companyUpdated.setName("New Name");

        when(companyRepository.save(any(Company.class))).thenReturn(companyUpdated);

        Company companySaved = companyService.saveCompany(companyUpdated);

        assertNotNull(companySaved);
        assertEquals("New Name", companySaved.getName());
        assertEquals("1", companySaved.getId());
        verify(companyRepository, times(1)).save(companyUpdated);

   }

   @Test
    void deleteCompanyById(){
        String companyId="c1";
        String employeeId="e1";
        String deviceId = "d1";

        Company company = new Company();
        company.setId(companyId);

        Employee employee = new Employee();
        employee.setId(employeeId);

        Device device = new Device();
        device.setSerialNumber(deviceId);

        when(deviceRepository.findByEmployeeId(employeeId)).thenReturn(List.of(device));
        when(employeeRepository.findByCompanyId(companyId)).thenReturn(List.of(employee));

        companyService.deleteById(companyId);

        verify(deviceRepository).deleteAll(List.of(device));
        verify(employeeRepository).deleteById(employeeId);
        verify(companyRepository, times(1)).deleteById(companyId);
   }

   @Test
    void notEmptyName(){
        Company company = new Company();
        company.setName("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        companyService.saveCompany(company));

       assertEquals("Company name cannot be empty", exception.getMessage());

       verify(companyRepository, times(0)).save(any());

    }
}

