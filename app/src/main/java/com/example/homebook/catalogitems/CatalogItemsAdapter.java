package com.example.homebook.catalogitems;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.R;
import com.example.homebook.catalog.CatalogViewModel;
import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.catalogitemsdata.CatalogItems;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.ViewHolderListBinding;
import com.example.homebook.services.DateTimeUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        notifyDataSetChanged();
    }

    public List<Item> getCatalogItemsList() {
        return catalogItemsList;
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

            this.binding.buttonAmount.setOnClickListener(view -> {
                final EditText input = new EditText(binding.getRoot().getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 100);
                input.setLayoutParams(lp);

                new MaterialAlertDialogBuilder(binding.getRoot().getContext())
                        .setTitle("Insert name of your category:")
                        .setView(input)
                        .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                            // nothing happens
                        })
                        .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                            // nothing happens
                        })
                        .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
                            String amount = input.getText().toString();
                            catalogItemsList.get(position).setAmount(Integer.parseInt(amount));
                            this.binding.amountToBuy.setText(amount);
                            this.binding.amountToBuy.setTextColor(Color.GREEN);
                        })
                        .show();
            });

        }
    }
}
