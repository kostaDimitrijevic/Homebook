package com.example.homebook.data.categorydata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class CategoryRepository {

    private final CategoryDao categoryDao;
    private final ExecutorService executorService;

    @Inject
    public CategoryRepository(
            CategoryDao categoryDao,
            ExecutorService executorService) {
        this.categoryDao = categoryDao;
        this.executorService = executorService;
    }

    public void insert(Category category){
        executorService.submit(() -> {
            categoryDao.insert(category);
        });
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryDao.getAllCategories();
    }
}
