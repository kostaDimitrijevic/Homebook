package com.example.homebook.subcategory;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.subcategorydata.SubcategoryRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SubcategoryViewModel extends ViewModel {

    private SubcategoryRepository subcategoryRepository;
    private SavedStateHandle savedStateHandle;

    @Inject
    public SubcategoryViewModel(@NonNull SavedStateHandle savedStateHandle, SubcategoryRepository subcategoryRepository) {
        this.savedStateHandle = savedStateHandle;
        this.subcategoryRepository = subcategoryRepository;
    }
}
