package com.example.homebook.catalogitems;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.catalog.CatalogViewModel;
import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.catalogitemsdata.CatalogItems;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.ViewHolderListBinding;
import com.example.homebook.services.DateTimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CatalogItemsAdapter extends RecyclerView.Adapter<CatalogItemsAdapter.ListViewHolder> {

    private List<Item> catalogItemsList = new ArrayList<>();
    private CatalogViewModel catalogViewModel;

    public CatalogItemsAdapter(CatalogViewModel catalogViewModel) {
        this.catalogViewModel = catalogViewModel;
    }

    public void setCatalogItemsList(List<Item> catalogItemsList) {
        this.catalogItemsList = catalogItemsList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderListBinding binding = ViewHolderListBinding.inflate(layoutInflater, parent, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(catalogItemsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return catalogItemsList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderListBinding binding;

        public ListViewHolder(@NonNull ViewHolderListBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


        public void bind(Item catalogItem, int position) {
            this.binding.listItemLabel.setText(catalogItem.getItemName());
            this.binding.amountToBuy.setText("10");
            this.binding.amountToBuy.setTextColor(Color.GREEN);

            Date date = new Date();
            catalogViewModel.insertCatalog(new Catalog(0, catalogViewModel.getCatalogName(), 0, DateTimeUtil.getSimpleDateFormat().format(date)));
        }
    }
}
