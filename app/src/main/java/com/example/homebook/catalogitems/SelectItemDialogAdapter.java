package com.example.homebook.catalogitems;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.ViewHolderSelectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectItemDialogAdapter extends RecyclerView.Adapter<SelectItemDialogAdapter.SelectItemViewHolder> {

    private List<Item> itemList = new ArrayList<>();

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SelectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderSelectItemBinding binding = ViewHolderSelectItemBinding.inflate(layoutInflater, parent, false);

        return new SelectItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class SelectItemViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderSelectItemBinding binding;

        public SelectItemViewHolder(@NonNull ViewHolderSelectItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }


        public void bind(Item item, int position) {

        }
    }
}
