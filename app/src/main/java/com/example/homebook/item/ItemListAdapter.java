package com.example.homebook.item;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.ViewHolderItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private List<Item> itemList = new ArrayList<>();

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderItemBinding binding = ViewHolderItemBinding.inflate(layoutInflater, parent, false);

        return new ItemListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderItemBinding binding;

        public ItemListViewHolder(@NonNull ViewHolderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull Item item, int position){

            binding.itemLabel.setText(item.getItemName());
            if(item.getAmount() == 0){
                binding.itemAmount.setText("OUT OF STOCK!");
                binding.itemAmount.setTextColor(Color.RED);
                binding.itemCountLabel.setText("");
            }
            else{
                binding.itemAmount.setText(String.valueOf(item.getAmount()));
                binding.itemAmount.setTextColor(Color.BLACK);
                binding.itemCountLabel.setText("Amount:");
                binding.itemCountLabel.setTextColor(Color.BLACK);
            }
        }
    }
}
