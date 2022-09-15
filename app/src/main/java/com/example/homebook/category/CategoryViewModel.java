package com.example.homebook.category;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.categorydata.Category;
import com.example.homebook.data.categorydata.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoryViewModel extends ViewModel {

    private final CategoryRepository categoryRepository;
    private final SavedStateHandle savedStateHandle;

    private final LiveData<List<Category>> categories;
    private List<String> imageUrls;

    @Inject
    public CategoryViewModel(@NonNull SavedStateHandle savedStateHandle, CategoryRepository categoryRepository){

        this.savedStateHandle = savedStateHandle;
        this.categoryRepository = categoryRepository;

        this.categories = categoryRepository.getAllCategories();
    }

    public void insertCategory(Category category) { categoryRepository.insert(category); }

    public LiveData<List<Category>> getAllCategories(){
        return categories;
    }

    public long getCategoryIdByName(String category) { return categoryRepository.getCategoryIdByName(category); }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
