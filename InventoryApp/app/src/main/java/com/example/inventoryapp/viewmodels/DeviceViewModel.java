package com.example.inventoryapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventoryapp.models.Device;
import com.example.inventoryapp.network.ApiService;
import com.example.inventoryapp.network.RetrofitClient;
import com.example.inventoryapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<List<Device>> devices = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Device> selectedDevice = new MutableLiveData<>();
    private final MutableLiveData<Integer> errorResId = new MutableLiveData<>();

    public DeviceViewModel() {
        this(RetrofitClient.getApiService());
    }

    public DeviceViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Device>> getDevices() {
        return devices;
    }

    public LiveData<Device> getSelectedDevice() {
        return selectedDevice;
    }

    public LiveData<Integer> getErrorResId() {
        return errorResId;
    }

    public void loadDevices() {
        apiService.getDevices().enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    devices.setValue(response.body());
                } else {
                    errorResId.setValue(R.string.FailedToLoadDevices);
                }
            }
            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void addDevice(Device device) {
        apiService.createDevice(device).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Device> currentList = devices.getValue();
                    if (currentList != null) {
                        List<Device> updatedList = new ArrayList<>(currentList);
                        updatedList.add(response.body());
                        devices.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToAddDevice);
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void getDeviceById(String serialNumber) {
        apiService.getDeviceById(serialNumber).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful() && response.body() != null) {
                    selectedDevice.setValue(response.body());
                } else {
                    errorResId.setValue(R.string.DeviceNotFound);
                }
            }
            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void updateDeviceById(String serialNumber, Device device) {
        apiService.updateDevice(serialNumber, device).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Device> currentList = devices.getValue();
                    if (currentList != null) {
                        List<Device> updatedList = new ArrayList<>(currentList);
                        for (int i = 0; i < updatedList.size(); i++) {
                            if (updatedList.get(i).getSerialNumber().equals(serialNumber)) {
                                updatedList.set(i, response.body());
                                break;
                            }
                        }
                        devices.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToUpdateDevice);
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void deleteDeviceById(String serialNumber) {
        apiService.deleteDevice(serialNumber).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Device> currentList = devices.getValue();
                    if (currentList != null) {
                        List<Device> updatedList = new ArrayList<>(currentList);
                        updatedList.removeIf(c -> c.getSerialNumber().equals(serialNumber));
                        devices.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToDeleteDevice);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }
}
