package com.example.homebook.data.categorydata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

    public long getCategoryIdByName(String category) {
        Future<Long> task = executorService.submit(() -> categoryDao.getCategoryIdByName(category));
        try {
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
