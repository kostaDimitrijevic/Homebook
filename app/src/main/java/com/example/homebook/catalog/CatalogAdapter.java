package com.example.homebook.catalog;

import android.graphics.Color;
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

    public interface Callback<T>{
        void Invoke(T parameter);
    }

    private List<Catalog> catalogs = new ArrayList<>();
    private CatalogViewModel catalogViewModel;
    private Callback<String> callback;

    public CatalogAdapter(CatalogViewModel catalogViewModel, Callback<String> callback) {
        this.catalogViewModel = catalogViewModel;
        this.callback = callback;
    }

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
            binding.userList.setText(catalog.getUser());
            if(catalog.getStatus() == 0){
                binding.status.setText("ACTIVE");
                binding.status.setTextColor(Color.GREEN);
            }
            else{
                binding.status.setText("PROCESSED");
                binding.status.setTextColor(Color.YELLOW);
            }

            binding.buttonDeleteList.setOnClickListener(view -> {
                catalogViewModel.deleteCatalogItems(catalog.getId());
                catalogViewModel.deleteCatalog(catalog.getId());
            });

            binding.buttonShowList.setOnClickListener(view -> {
                catalogViewModel.setCurrentCatalog(catalog.getId());
                callback.Invoke(catalog.getCatalogName());
            });
        }
    }
}
