package com.example.inventoryapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.inventoryapp.models.Company;
import com.example.inventoryapp.models.Device;
import com.example.inventoryapp.models.Employee;
import com.example.inventoryapp.network.ApiService;
import com.example.inventoryapp.viewmodels.CompanyViewModel;
import com.example.inventoryapp.viewmodels.DeviceViewModel;
import com.example.inventoryapp.viewmodels.EmployeeViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(JUnit4.class)
public class ExampleUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ApiService apiService;

    @Mock
    private Call<Company> companyCall;

    @Mock
    private Call<Employee> employeeCall;

    @Mock
    private Call<Device> deviceCall;

    @Mock
    private Call<Void> voidCall;

    private CompanyViewModel companyViewModel;
    private EmployeeViewModel employeeViewModel;
    private DeviceViewModel deviceViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        companyViewModel = new CompanyViewModel(apiService);
        employeeViewModel = new EmployeeViewModel(apiService);
        deviceViewModel = new DeviceViewModel(apiService);
    }

    @Test
    public void testAddCompany() {
        Company company = new Company("Test Company", "123456789");
        when(apiService.createCompany(any(Company.class))).thenReturn(companyCall);

        companyViewModel.addCompany(company);

        ArgumentCaptor<Callback<Company>> callbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(companyCall).enqueue(callbackCaptor.capture());

        Response<Company> response = Response.success(company);
        callbackCaptor.getValue().onResponse(companyCall, response);

        List<Company> companies = companyViewModel.getCompanies().getValue();
        assertNotNull(companies);
        assertEquals(1, companies.size());
        assertEquals("Test Company", companies.get(0).getName());
    }

    @Test
    public void testDeleteCompany() {
        Company company = new Company("To Delete", "Address");
        company.setId("test-id");
        when(apiService.createCompany(any(Company.class))).thenReturn(companyCall);
        companyViewModel.addCompany(company);
        
        ArgumentCaptor<Callback<Company>> addCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(companyCall).enqueue(addCallbackCaptor.capture());
        addCallbackCaptor.getValue().onResponse(companyCall, Response.success(company));
        
        assertEquals(1, companyViewModel.getCompanies().getValue().size());

        when(apiService.deleteCompany(anyString())).thenReturn(voidCall);

        companyViewModel.deleteCompanyById("test-id");

        ArgumentCaptor<Callback<Void>> deleteCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(voidCall).enqueue(deleteCallbackCaptor.capture());
        deleteCallbackCaptor.getValue().onResponse(voidCall, Response.success(null));

        List<Company> companies = companyViewModel.getCompanies().getValue();
        assertNotNull(companies);
        assertEquals(0, companies.size());
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee("John Doe", "john@example.com", "comp-id");
        when(apiService.createEmployee(any(Employee.class))).thenReturn(employeeCall);

        employeeViewModel.addEmployee(employee);

        ArgumentCaptor<Callback<Employee>> callbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(employeeCall).enqueue(callbackCaptor.capture());

        Response<Employee> response = Response.success(employee);
        callbackCaptor.getValue().onResponse(employeeCall, response);

        List<Employee> employees = employeeViewModel.getEmployees().getValue();
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee("To Delete", "delete@example.com", "comp-id");
        employee.setId("emp-id");
        when(apiService.createEmployee(any(Employee.class))).thenReturn(employeeCall);
        employeeViewModel.addEmployee(employee);

        ArgumentCaptor<Callback<Employee>> addCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(employeeCall).enqueue(addCallbackCaptor.capture());
        addCallbackCaptor.getValue().onResponse(employeeCall, Response.success(employee));

        assertEquals(1, employeeViewModel.getEmployees().getValue().size());

        when(apiService.deleteEmployee(anyString())).thenReturn(voidCall);

        employeeViewModel.deleteEmployeeById("emp-id");

        ArgumentCaptor<Callback<Void>> deleteCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(voidCall).enqueue(deleteCallbackCaptor.capture());
        deleteCallbackCaptor.getValue().onResponse(voidCall, Response.success(null));

        List<Employee> employees = employeeViewModel.getEmployees().getValue();
        assertNotNull(employees);
        assertEquals(0, employees.size());
    }

    @Test
    public void testAddDevice() {
        Device device = new Device("HP", "Laptop", "emp-id");
        when(apiService.createDevice(any(Device.class))).thenReturn(deviceCall);

        deviceViewModel.addDevice(device);

        ArgumentCaptor<Callback<Device>> callbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(deviceCall).enqueue(callbackCaptor.capture());

        Response<Device> response = Response.success(device);
        callbackCaptor.getValue().onResponse(deviceCall, response);

        List<Device> devices = deviceViewModel.getDevices().getValue();
        assertNotNull(devices);
        assertEquals(1, devices.size());
        assertEquals("HP", devices.get(0).getName());
    }

    @Test
    public void testDeleteDevice() {
        Device device = new Device("To Delete", "Laptop", "emp-id");
        device.setSerialNumber("123");
        when(apiService.createDevice(any(Device.class))).thenReturn(deviceCall);
        deviceViewModel.addDevice(device);

        ArgumentCaptor<Callback<Device>> addCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(deviceCall).enqueue(addCallbackCaptor.capture());
        addCallbackCaptor.getValue().onResponse(deviceCall, Response.success(device));

        assertEquals(1, deviceViewModel.getDevices().getValue().size());

        when(apiService.deleteDevice(anyString())).thenReturn(voidCall);

        deviceViewModel.deleteDeviceById("123");

        ArgumentCaptor<Callback<Void>> deleteCallbackCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(voidCall).enqueue(deleteCallbackCaptor.capture());
        deleteCallbackCaptor.getValue().onResponse(voidCall, Response.success(null));

        List<Device> devices = deviceViewModel.getDevices().getValue();
        assertNotNull(devices);
        assertEquals(0, devices.size());
    }
}