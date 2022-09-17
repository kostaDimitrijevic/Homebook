package com.example.homebook.subcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.category.CategoryFragmentDirections;
import com.example.homebook.data.subcategorydata.Subcategory;
import com.example.homebook.databinding.ViewHolderSubcategoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder> {

    public interface Callback<T>{
        void Invoke(T parameter);
    }

    private List<Subcategory> subcategories = new ArrayList<>();
    private SubcategoryViewModel subcategoryViewModel;
    private final Callback<Long> callback;

    public SubcategoryAdapter(SubcategoryViewModel subcategoryViewModel, Callback<Long> callback) {
        this.subcategoryViewModel = subcategoryViewModel;
        this.callback = callback;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubcategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderSubcategoryBinding binding = ViewHolderSubcategoryBinding.inflate(layoutInflater, parent, false);

        return new SubcategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryViewHolder holder, int position) {
        holder.bind(subcategories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return subcategories.size();
    }

    public class SubcategoryViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderSubcategoryBinding binding;

        public SubcategoryViewHolder(@NonNull ViewHolderSubcategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Subcategory subcategory, int position) {
            this.binding.subcategoryLabel.setText(subcategory.getSubName());

            this.binding.buttonSubDelete.setOnClickListener(view -> {
                subcategoryViewModel.deleteSubcategory(subcategories.get(position).getId());
            });

            this.binding.buttonSubItems.setOnClickListener(view -> {
                callback.Invoke(subcategories.get(position).getId());
            });
        }
    }
}
