package com.example.inventoryapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.R;
import com.example.inventoryapp.adapters.CompanyAdapter;
import com.example.inventoryapp.adapters.EmployeeAdapter;
import com.example.inventoryapp.models.Company;
import com.example.inventoryapp.models.Employee;
import com.example.inventoryapp.viewmodels.CompanyViewModel;
import com.example.inventoryapp.viewmodels.EmployeeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CompaniesFragment extends Fragment implements CompanyAdapter.OnCompanyClickListener {

    private RecyclerView recyclerView;
    private CompanyAdapter adapter;
    private CompanyViewModel viewModel;
    private EmployeeViewModel employeeViewModel;
    private FloatingActionButton fab;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CompaniesFragment() {
    }

    public static CompaniesFragment newInstance(String param1, String param2) {
        CompaniesFragment fragment = new CompaniesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_companies, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> showCompanyDialog(null));

        viewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        viewModel.getCompanies().observe(getViewLifecycleOwner(), companies -> {
            if (companies != null) {
                adapter = new CompanyAdapter(companies, this);
                recyclerView.setAdapter(adapter);
            }
        });

        viewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        employeeViewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        viewModel.loadCompanies();
        employeeViewModel.loadEmployees();

        return root;
    }

    @Override
    public void onEditClick(Company company) {
        showCompanyDialog(company);

    }

    @Override
    public void onDeleteClick(Company company) {
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.DeleteCompany))
                .setMessage(getResources().getString(R.string.AreSureYouWantToDeleteThisCompany))
                .setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
                    viewModel.deleteCompanyById(company.getId());
                    Toast.makeText(getContext(), getResources().getString(R.string.CompanyDeleted), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(getResources().getString(R.string.No), null)
                .show();
    }

    @Override
    public void onItemClick(Company company) {
        showEmployeesDialog(company);
    }

    private void showCompanyDialog(Company company) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.company_dialog, null);
        builder.setView(dialogView);

        android.widget.EditText editTextName = dialogView.findViewById(R.id.editTextText);
        android.widget.EditText editTextAddress = dialogView.findViewById(R.id.editTextText2);

        android.widget.Button btnConfirm = dialogView.findViewById(R.id.button3);
        android.widget.Button btnCancel = dialogView.findViewById(R.id.button4);

        if (company != null) {
            builder.setTitle(getResources().getString(R.string.EditCompany));
            editTextName.setText(company.getName());
            editTextAddress.setText(company.getAddress());
        } else {
            builder.setTitle(getResources().getString(R.string.AddCompany));
        }

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        btnConfirm.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();

            if (name.isEmpty()) {
                editTextName.setError(getResources().getString(R.string.NameIsRequired));
                return;
            }
            if (address.isEmpty()) {
                editTextAddress.setError(getResources().getString(R.string.AddressIsRequired));
                return;
            }

            if (company != null) {
                company.setName(name);
                company.setAddress(address);
                viewModel.updateCompanyById(company.getId(), company);
            } else {
                Company newCompany = new Company(name, address);
                viewModel.addCompany(newCompany);
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void showEmployeesDialog(Company company) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.list_dialog, null);
        builder.setView(dialogView);

        TextView title = dialogView.findViewById(R.id.DialogTitle);
        title.setText(getResources().getString(R.string.EmployeesOf)+" "+ company.getName());

        RecyclerView rvDialog = dialogView.findViewById(R.id.DialogItems);
        rvDialog.setLayoutManager(new LinearLayoutManager(getContext()));

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        employeeViewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            if (employees != null) {
                List<Employee> filtered = new ArrayList<>();
                for (Employee employee : employees) {
                    if (employee.getCompanyId() != null && employee.getCompanyId().equals(company.getId())) {
                        filtered.add(employee);
                    }
                }

                if (filtered.isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.NoEmployeesFoundForThisCompany), Toast.LENGTH_SHORT).show();
                } else {
                    rvDialog.setAdapter(new EmployeeAdapter(filtered, null));
                }
            }
        });

        dialogView.findViewById(R.id.btnDialogClose).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}