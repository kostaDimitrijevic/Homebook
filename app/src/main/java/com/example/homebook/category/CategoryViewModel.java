package com.example.homebook.category;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.Category;
import com.example.homebook.data.CategoryRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoryViewModel extends ViewModel {

    private final CategoryRepository categoryRepository;
    private final SavedStateHandle savedStateHandle;

    private final LiveData<List<Category>> categories;

    private static final String NEW_CATEGORY_ADDED = "new-added";
    private boolean newCategoryAdded = false;

    @Inject
    public CategoryViewModel(@NonNull SavedStateHandle savedStateHandle, CategoryRepository categoryRepository){

        this.savedStateHandle = savedStateHandle;
        this.categoryRepository = categoryRepository;

        this.categories = Transformations.switchMap(
                savedStateHandle.getLiveData(NEW_CATEGORY_ADDED, false),
                newCategoryAdded -> categoryRepository.getAllCategories()
        );
    }

    public void insertCategory(Category category) { categoryRepository.insert(category); }

    public LiveData<List<Category>> getAllCategories(){
        return categories;
    }

    public void invertAdded() {
        savedStateHandle.set(NEW_CATEGORY_ADDED, newCategoryAdded = !newCategoryAdded);
    }
}
