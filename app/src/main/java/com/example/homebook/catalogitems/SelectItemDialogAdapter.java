package com.example.homebook.catalogitems;

import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.ViewHolderSelectItemBinding;
import com.example.homebook.item.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectItemDialogAdapter extends RecyclerView.Adapter<SelectItemDialogAdapter.SelectItemViewHolder> {

    private List<Item> itemList = new ArrayList<>();
    private ItemViewModel itemViewModel;

    public SelectItemDialogAdapter(ItemViewModel itemViewModel) {
        this.itemViewModel = itemViewModel;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderSelectItemBinding binding = ViewHolderSelectItemBinding.inflate(layoutInflater, parent, false);

        return new SelectItemViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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


        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Item item, int position) {
            this.binding.selectItemLabel.setText(item.getItemName());
            this.binding.amountSelect.setText(String.valueOf(item.getAmount()));

            this.binding.checkbox.setOnClickListener(view -> {
                if(!this.binding.checkbox.isChecked()){
                    itemViewModel.getItemsToAdd().removeIf(item1 -> item1.getId() == item.getId());
                }
                else{
                    itemViewModel.getItemsToAdd().add(new Item(item.getId(), item.getItemName(), item.getIdC(), item.getIdS(), item.getAmount()));
                }
                this.binding.checkbox.setChecked(this.binding.checkbox.isChecked());
            });
        }
    }
}
