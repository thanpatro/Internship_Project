package com.example.inventoryapp.ui;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.R;
import com.example.inventoryapp.adapters.DeviceAdapter;
import com.example.inventoryapp.adapters.EmployeeAdapter;
import com.example.inventoryapp.models.Company;
import com.example.inventoryapp.models.Device;
import com.example.inventoryapp.models.Employee;
import com.example.inventoryapp.viewmodels.CompanyViewModel;
import com.example.inventoryapp.viewmodels.DeviceViewModel;
import com.example.inventoryapp.viewmodels.EmployeeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EmployeesFragment extends Fragment implements EmployeeAdapter.OnEmployeeClickListener {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private EmployeeViewModel employeeViewModel;
    private CompanyViewModel companyViewModel;
    private DeviceViewModel deviceViewModel;
    private FloatingActionButton fab;
    private List<Company> allCompanies = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EmployeesFragment() {
    }

    public static EmployeesFragment newInstance(String param1, String param2) {
        EmployeesFragment fragment = new EmployeesFragment();
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
        View root = inflater.inflate(R.layout.fragment_employees, container, false);

        recyclerView = root.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab = root.findViewById(R.id.floatingActionButton2);

        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        companyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        deviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);

        employeeViewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            if (employees != null) {
                adapter = new EmployeeAdapter(employees, this);
                recyclerView.setAdapter(adapter);
            }
        });

        companyViewModel.getCompanies().observe(getViewLifecycleOwner(), companies -> {
            this.allCompanies = companies;
        });

        employeeViewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        companyViewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        deviceViewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        fab.setOnClickListener(v -> showEmployeeDialog(null));

        companyViewModel.loadCompanies();
        employeeViewModel.loadEmployees();
        deviceViewModel.loadDevices();

        return root;
    }

    @Override
    public void onItemClick(Employee employee) {
        showDevicesDialog(employee);
    }

    @Override
    public void onEditClick(Employee employee) {
        showEmployeeDialog(employee);
    }

    @Override
    public void onDeleteClick(Employee employee) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.DeleteEmployee))
                .setMessage(getResources().getString(R.string.AreSureYouWantToDeleteThisEmployee))
                .setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
                    employeeViewModel.deleteEmployeeById(employee.getId());
                    Toast.makeText(getContext(), getResources().getString(R.string.EmployeeDeleted), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(getResources().getString(R.string.No), null)
                .show();
    }

    private void showEmployeeDialog(Employee employee) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.employee_dialog, null);
        builder.setView(dialogView);

        android.widget.EditText editTextName = dialogView.findViewById(R.id.editTextText3);
        android.widget.Spinner spinner = dialogView.findViewById(R.id.spinner);
        android.widget.EditText editTextEmail = dialogView.findViewById(R.id.editTextTextEmailAddress);

        android.widget.Button btnConfirm = dialogView.findViewById(R.id.button5);
        android.widget.Button btnCancel = dialogView.findViewById(R.id.button6);

        List<String> companyNames = new ArrayList<>();
        companyNames.add(getResources().getString(R.string.SelectCompany));
        if (allCompanies != null) {
            for (Company company : allCompanies) {
                companyNames.add(company.getName());
            }
        }

        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, companyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (employee != null) {
            builder.setTitle(getResources().getString(R.string.EditEmployee));
            editTextName.setText(employee.getName());
            editTextEmail.setText(employee.getEmail());

            if (employee.getCompanyId() != null) {
                for (int i = 0; i < allCompanies.size(); i++) {
                    if (allCompanies.get(i).getId().equals(employee.getCompanyId())) {
                        spinner.setSelection(i + 1);
                        break;
                    }
                }
            }
        } else {
            builder.setTitle(getResources().getString(R.string.AddEmployee));
        }

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        btnConfirm.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            int selectedPosition = spinner.getSelectedItemPosition();

            if (name.isEmpty()) {
                editTextName.setError(getResources().getString(R.string.NameIsRequired));
                return;
            }
            if (email.isEmpty()) {
                editTextEmail.setError(getResources().getString(R.string.EmailIsRequired));
                return;
            }
            if (selectedPosition <= 0 || allCompanies == null || allCompanies.isEmpty()) {
                Toast.makeText(getContext(), getResources().getString(R.string.PleaseSelectACompany), Toast.LENGTH_SHORT).show();
                return;
            }

            String companyId = allCompanies.get(selectedPosition - 1).getId();

            if (employee != null) {
                employee.setName(name);
                employee.setEmail(email);
                employee.setCompanyId(companyId);
                employeeViewModel.updateEmployeeById(employee.getId(), employee);
            } else {
                Employee newEmployee = new Employee(name, email, companyId);
                employeeViewModel.addEmployee(newEmployee);
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void showDevicesDialog(Employee employee) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.list_dialog, null);
        builder.setView(dialogView);

        TextView title = dialogView.findViewById(R.id.DialogTitle);
        title.setText(getResources().getString(R.string.DevicesOf)+" "+ employee.getName());

        RecyclerView rvDialog = dialogView.findViewById(R.id.DialogItems);
        rvDialog.setLayoutManager(new LinearLayoutManager(getContext()));

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        deviceViewModel.getDevices().observe(getViewLifecycleOwner(), devices -> {
            if (devices != null) {
                List<Device> filtered = new ArrayList<>();
                for (Device device : devices) {
                    if (device.getEmployeeId() != null && device.getEmployeeId().equals(employee.getId())) {
                        filtered.add(device);
                    }
                }

                if (filtered.isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.NoDevicesFoundForThisEmployee), Toast.LENGTH_SHORT).show();
                } else {
                    rvDialog.setAdapter(new DeviceAdapter(filtered, null));
                }
            }
        });

        dialogView.findViewById(R.id.btnDialogClose).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}