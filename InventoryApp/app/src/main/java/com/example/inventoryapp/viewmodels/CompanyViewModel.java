package com.example.inventoryapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.inventoryapp.models.Company;
import com.example.inventoryapp.network.ApiService;
import com.example.inventoryapp.network.RetrofitClient;
import com.example.inventoryapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<List<Company>> companies = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Company> selectedCompany = new MutableLiveData<>();
    private final MutableLiveData<Integer> errorResId = new MutableLiveData<>();

    public CompanyViewModel() {
        this(RetrofitClient.getApiService());
    }

    public CompanyViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Company>> getCompanies() {
        return companies;
    }

    public LiveData<Company> getSelectedCompany() {
        return selectedCompany;
    }

    public LiveData<Integer> getErrorResId() {
        return errorResId;
    }

    public void loadCompanies() {
        apiService.getCompanies().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    companies.setValue(response.body());
                } else {
                    errorResId.setValue(R.string.FailedToLoadCompanies);
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void addCompany(Company company) {
        apiService.createCompany(company).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Company> currentList = companies.getValue();
                    if (currentList != null) {
                        List<Company> updatedList = new ArrayList<>(currentList);
                        updatedList.add(response.body());
                        companies.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToAddCompany);
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void getCompanyById(String id) {
        apiService.getCompanyById(id).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful() && response.body() != null) {
                    selectedCompany.setValue(response.body());
                } else {
                    errorResId.setValue(R.string.CompanyNotFound);
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void updateCompanyById(String id, Company company) {
        apiService.updateCompany(id, company).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Company> currentList = companies.getValue();
                    if (currentList != null) {
                        List<Company> updatedList = new ArrayList<>(currentList);
                        for (int i = 0; i < updatedList.size(); i++) {
                            if (updatedList.get(i).getId().equals(id)) {
                                updatedList.set(i, response.body());
                                break;
                            }
                        }
                        companies.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToUpdateCompany);
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }

    public void deleteCompanyById(String id) {
        apiService.deleteCompany(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Company> currentList = companies.getValue();
                    if (currentList != null) {
                        List<Company> updatedList = new ArrayList<>(currentList);
                        updatedList.removeIf(c -> c.getId().equals(id));
                        companies.setValue(updatedList);
                    }
                } else {
                    errorResId.setValue(R.string.FailedToDeleteCompany);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorResId.setValue(R.string.NetworkError);
            }
        });
    }
}
