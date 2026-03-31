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
import android.widget.Toast;

import com.example.inventoryapp.R;
import com.example.inventoryapp.adapters.DeviceAdapter;
import com.example.inventoryapp.models.Device;
import com.example.inventoryapp.models.Employee;
import com.example.inventoryapp.viewmodels.DeviceViewModel;
import com.example.inventoryapp.viewmodels.EmployeeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DevicesFragment extends Fragment implements DeviceAdapter.OnDeviceClickListener {

    private RecyclerView recyclerView;
    private DeviceAdapter adapter;
    private DeviceViewModel deviceViewModel;
    private EmployeeViewModel employeeViewModel;
    private FloatingActionButton fab;
    private List<Employee> allEmployees = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public DevicesFragment() {
    }

    public static DevicesFragment newInstance(String param1, String param2) {
        DevicesFragment fragment = new DevicesFragment();
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
        View root = inflater.inflate(R.layout.fragment_devices, container, false);

        recyclerView = root.findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab = root.findViewById(R.id.floatingActionButton3);

        deviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        deviceViewModel.getDevices().observe(getViewLifecycleOwner(), devices -> {
            if (devices != null) {
                adapter = new DeviceAdapter(devices, this);
                recyclerView.setAdapter(adapter);
            }
        });

        employeeViewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            this.allEmployees = employees;
        });

        deviceViewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        employeeViewModel.getErrorResId().observe(getViewLifecycleOwner(), resId -> {
            if (resId != null) {
                Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
            }
        });

        fab.setOnClickListener(v -> showDeviceDialog(null));

        deviceViewModel.loadDevices();
        employeeViewModel.loadEmployees();

        return root;
    }

    @Override
    public void onEditClick(Device device) {
        showDeviceDialog(device);
    }

    @Override
    public void onDeleteClick(Device device) {
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.DeleteDevice))
                .setMessage(getResources().getString(R.string.AreSureYouWantToDeleteThisDevice))
                .setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
                    deviceViewModel.deleteDeviceById(device.getSerialNumber());
                    Toast.makeText(getContext(), getResources().getString(R.string.DeviceDeleted), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(getResources().getString(R.string.No), null)
                .show();
    }

    private void showDeviceDialog(Device device) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.device_dialog, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextText4);
        android.widget.Spinner spinner = dialogView.findViewById(R.id.spinner2);
        EditText editTextType = dialogView.findViewById(R.id.editTextText5);

        android.widget.Button btnConfirm = dialogView.findViewById(R.id.button7);
        android.widget.Button btnCancel = dialogView.findViewById(R.id.button8);

        List<String> employeeNames = new ArrayList<>();
        employeeNames.add(getResources().getString(R.string.SelectEmployee));
        if (allEmployees != null) {
            for (Employee employee : allEmployees) {
                employeeNames.add(employee.getName());
            }
        }

        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, employeeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (device != null) {
            builder.setTitle(getResources().getString(R.string.EditDevice));
            editTextName.setText(device.getName());
            editTextType.setText(device.getType());

            if (device.getEmployeeId() != null) {
                for (int i = 0; i < allEmployees.size(); i++) {
                    if (allEmployees.get(i).getId().equals(device.getEmployeeId())) {
                        spinner.setSelection(i+1);
                        break;
                    }
                }
            }
        } else {
            builder.setTitle(getResources().getString(R.string.AddDevice));
        }

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        btnConfirm.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String type = editTextType.getText().toString().trim();
            int selectedPosition = spinner.getSelectedItemPosition();

            if (name.isEmpty()) {
                editTextName.setError(getResources().getString(R.string.NameIsRequired));
                return;
            }
            if (type.isEmpty()) {
                editTextType.setError(getResources().getString(R.string.TypeIsRequired));
                return;
            }
            if (selectedPosition <= 0 || allEmployees == null || allEmployees.isEmpty()) {
                Toast.makeText(getContext(), getResources().getString(R.string.PleaseSelectAnEmployee), Toast.LENGTH_SHORT).show();
                return;
            }

            Employee selectedEmployee = allEmployees.get(selectedPosition-1);
            String employeeId = selectedEmployee.getId();
            String companyId = selectedEmployee.getCompanyId();

            if (device != null) {
                device.setName(name);
                device.setType(type);
                device.setEmployeeId(employeeId);
                device.setCompanyId(companyId);
                deviceViewModel.updateDeviceById(device.getSerialNumber(), device);
            } else {
                Device newDevice = new Device(name, type, employeeId, companyId);
                deviceViewModel.addDevice(newDevice);
            }

            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}