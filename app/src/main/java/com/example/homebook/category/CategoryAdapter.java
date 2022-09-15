package com.example.homebook.category;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.homebook.data.categorydata.Category;
import com.example.homebook.databinding.ViewHolderCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface Callback<T>{
        void Invoke(T parameter);
    }

    private List<Category> categories = new ArrayList<>();
    private final Callback<String> callbackCategoryName;
    private CategoryViewModel viewModel;

    public CategoryAdapter(CategoryViewModel viewModel, Callback<String> callbackCategoryName) {
        this.callbackCategoryName = callbackCategoryName;
        this.viewModel = viewModel;
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderCategoryBinding viewHolderCategoryBinding = ViewHolderCategoryBinding.inflate(layoutInflater, parent, false);

        return new CategoryAdapter.CategoryViewHolder(viewHolderCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public ViewHolderCategoryBinding binding;

        public CategoryViewHolder(@NonNull ViewHolderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.buttonShowItems.setOnClickListener(view -> {
                callbackCategoryName.Invoke(this.binding.categoryLabel.getText().toString());
            });
        }

        public void bind(Category category, int index){
            binding.categoryLabel.setText(category.getCategoryName());
            Glide.with(binding.getRoot()).load(viewModel.getImageUrls().get(index)).into(binding.categoryImage);
        }
    }
}
