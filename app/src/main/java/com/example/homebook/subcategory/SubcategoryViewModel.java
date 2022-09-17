package com.example.homebook.subcategory;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.subcategorydata.Subcategory;
import com.example.homebook.data.subcategorydata.SubcategoryRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SubcategoryViewModel extends ViewModel {

    private SubcategoryRepository subcategoryRepository;
    private SavedStateHandle savedStateHandle;
    private LiveData<List<Subcategory>> subcategoryList;
    private long currentCategoryId;

    private static final String CURRENT_CATEGORY = "current-category-sub";

    @Inject
    public SubcategoryViewModel(@NonNull SavedStateHandle savedStateHandle, SubcategoryRepository subcategoryRepository) {
        this.savedStateHandle = savedStateHandle;
        this.subcategoryRepository = subcategoryRepository;

        subcategoryList = Transformations.switchMap(this.savedStateHandle.getLiveData(CURRENT_CATEGORY, 0L),
                idCat -> this.subcategoryRepository.getSubcategoriesByCatId(idCat));
    }

    public void insertSubcategory(Subcategory subcategory){
        subcategoryRepository.insertSubcategory(subcategory.getSubName(), subcategory.getIdCat());
    }

    public LiveData<List<Subcategory>> getSubcategories(){
        return subcategoryList;
    }

    public long getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(long currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
        this.savedStateHandle.set(CURRENT_CATEGORY, currentCategoryId);
    }

    public void deleteSubcategory(long id) {
        this.subcategoryRepository.deleteSubcategory(id);
    }

    public String getSubcategoryNameById(long id){
        return this.subcategoryRepository.getSubcategoryNameById(id);
    }
}
