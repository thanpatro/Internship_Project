package com.example.inventoryapp.network;

import com.example.inventoryapp.models.Company;
import com.example.inventoryapp.models.Device;
import com.example.inventoryapp.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("companies")
    Call<List<Company>> getCompanies();

    @GET("companies/{id}")
    Call<Company> getCompanyById(@Path("id") String id);

    @POST("companies")
    Call<Company> createCompany(@Body Company company);

    @PUT("companies/{id}")
    Call<Company> updateCompany(@Path("id") String id,@Body Company company);

    @DELETE("companies/{id}")
    Call<Void> deleteCompany(@Path("id") String id);

    @GET("employees")
    Call<List<Employee>> getEmployees();

    @GET("employees/{id}")
    Call<Employee> getEmployeeById(@Path("id") String id);

    @POST("employees")
    Call<Employee> createEmployee(@Body Employee employee);

    @PUT("employees/{id}")
    Call<Employee> updateEmployee(@Path("id") String id,@Body Employee employee);

    @DELETE("employees/{id}")
    Call<Void> deleteEmployee(@Path("id") String id);

    @GET("devices")
    Call<List<Device>> getDevices();

    @GET("devices/{id}")
    Call<Device> getDeviceById(@Path("id") String id);

    @POST("devices")
    Call<Device> createDevice(@Body Device device);

    @PUT("devices/{id}")
    Call<Device> updateDevice(@Path("id") String id, @Body Device device);

    @DELETE("devices/{id}")
    Call<Void> deleteDevice(@Path("id") String id);
}
