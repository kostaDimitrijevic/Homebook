package com.example.homebook.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.databinding.ViewHolderCatalogBinding;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private List<Catalog> catalogs = new ArrayList<>();

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderCatalogBinding binding = ViewHolderCatalogBinding.inflate(inflater, parent, false);
        return new CatalogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        holder.bind(catalogs.get(position), position);
    }

    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {

        ViewHolderCatalogBinding binding;
        public CatalogViewHolder(@NonNull ViewHolderCatalogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Catalog catalog, int position){
            binding.catalogDate.setText(catalog.getDate());
            binding.catalogLabel.setText(catalog.getCatalogName());
        }
    }
}
