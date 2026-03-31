package com.example.inventoryapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventoryapp.models.Employee;
import com.example.inventoryapp.network.ApiService;
import com.example.inventoryapp.network.RetrofitClient;
import com.example.inventoryapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Employee> selectedEmployee = new MutableLiveData<>();
    private final MutableLiveData<Integer> errorResId = new MutableLiveData<>();

    public EmployeeViewModel() {
        this(RetrofitClient.getApiService());
    }

    public EmployeeViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public LiveData<Employee> getSelectedEmployee() {
        return selectedEmployee;
    }

    public LiveData<Integer> getErrorResId() {
        return errorResId;
    }

    public void loadEmployees() {
        apiService.getEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    employees.setValue(response.body());
                } else {
                    errorResId.setValue(R.string.FailedToLoadEmployees);
                }
            }
            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void addEmployee(Employee employee) {
        apiService.createEmployee(employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> currentList = employees.getValue();
                    if (currentList != null) {
                        List<Employee> updatedList = new ArrayList<>(currentList);
                        updatedList.add(response.body());
                        employees.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToAddEmployee);
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void getEmployeeById(String id) {
        apiService.getEmployeeById(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    selectedEmployee.setValue(response.body());
                } else {
                    errorResId.setValue(R.string.EmployeeNotFound);
                }
            }
            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void updateEmployeeById(String id, Employee employee) {
        apiService.updateEmployee(id, employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> currentList = employees.getValue();
                    if (currentList != null) {
                        List<Employee> updatedList = new ArrayList<>(currentList);
                        for (int i = 0; i < updatedList.size(); i++) {
                            if (updatedList.get(i).getId().equals(id)) {
                                updatedList.set(i, response.body());
                                break;
                            }
                        }
                        employees.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToUpdateEmployee);
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void deleteEmployeeById(String id) {
        apiService.deleteEmployee(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Employee> currentList = employees.getValue();
                    if (currentList != null) {
                        List<Employee> updatedList = new ArrayList<>(currentList);
                        updatedList.removeIf(c -> c.getId().equals(id));
                        employees.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToDeleteEmployee);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }
}
