package com.example.inventoryapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.R;
import com.example.inventoryapp.models.Device;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> devices;
    private OnDeviceClickListener listener;

    public DeviceAdapter(List<Device> devices, OnDeviceClickListener listener) {
        this.devices = devices;
        this.listener = listener;
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textType;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textDeviceName);
            textType = itemView.findViewById(R.id.textDeviceType);
            btnEdit = itemView.findViewById(R.id.btnEdit3);
            btnDelete = itemView.findViewById(R.id.btnDelete3);
        }
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        Device device = devices.get(position);
        holder.textName.setText(device.getName());
        holder.textType.setText(device.getType());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(device);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(device);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices != null ? devices.size() : 0;
    }

    public interface OnDeviceClickListener {
        void onEditClick(Device device);
        void onDeleteClick(Device device);
    }
}
