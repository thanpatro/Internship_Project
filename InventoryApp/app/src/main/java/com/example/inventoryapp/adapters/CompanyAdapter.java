// decide to make custom listeners because in this way we have reusability
// and we can use the adapter in different parts. Also, we have separation of
// concerns and better maintenance of the code.

package com.example.inventoryapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.R;
import com.example.inventoryapp.models.Company;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private List<Company> companies;
    private OnCompanyClickListener listener;


    public CompanyAdapter(List<Company> companies, OnCompanyClickListener listener) {
        this.companies = companies;
        this.listener = listener;
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textAddress;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textCompanyName);
            textAddress = itemView.findViewById(R.id.textCompanyAddress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.textName.setText(company.getName());
        holder.textAddress.setText(company.getAddress());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(company);
            }
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (listener!=null){
               listener.onDeleteClick(company);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(company);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies != null ? companies.size() : 0;
    }

    public interface OnCompanyClickListener {
        void onEditClick(Company company);
        void onDeleteClick(Company company);
        void onItemClick(Company company);
    }
}
