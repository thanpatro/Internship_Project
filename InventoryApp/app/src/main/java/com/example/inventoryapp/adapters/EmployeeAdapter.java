package com.example.inventoryapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.R;
import com.example.inventoryapp.models.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>{

    private List<Employee> employees;
    private OnEmployeeClickListener listener;

    public EmployeeAdapter(List<Employee> employees, OnEmployeeClickListener listener) {
        this.employees = employees;
        this.listener = listener;
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textEmail;
        ImageButton btnEdit;
        ImageButton btnDelete;


        public EmployeeViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textEmployeeName);
            textEmail = itemView.findViewById(R.id.textEmployeeEmail);
            btnEdit = itemView.findViewById(R.id.btnEdit2);
            btnDelete = itemView.findViewById(R.id.btnDelete2);
        }
    }

        @Override
        public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
            return new EmployeeAdapter.EmployeeViewHolder(view);
        }

        public void onBindViewHolder(EmployeeViewHolder holder, int position) {
            Employee employee = employees.get(position);
            holder.textName.setText(employee.getName());
            holder.textEmail.setText(employee.getEmail());

            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(employee);
                }
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(employee);
                }
            });

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(employee);
                }
            });
        }

        @Override
        public int getItemCount() {
            return employees != null ? employees.size() : 0;
        }

        public interface OnEmployeeClickListener {
            void onEditClick(Employee employee);
            void onDeleteClick(Employee employee);
            void onItemClick(Employee employee);
        }
    }
