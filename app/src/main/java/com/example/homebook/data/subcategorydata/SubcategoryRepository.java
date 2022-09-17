package com.example.homebook.data.subcategorydata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class SubcategoryRepository {

    private final SubcategoryDao subcategoryDao;
    private final ExecutorService executorService;

    @Inject
    public SubcategoryRepository(SubcategoryDao subcategoryDao, ExecutorService executorService) {
        this.subcategoryDao = subcategoryDao;
        this.executorService = executorService;
    }

    public void insertSubcategory(@NonNull String subName, long idCat){
        executorService.submit(() -> {
            subcategoryDao.insert(subName, idCat);
        });
    }

    public LiveData<List<Subcategory>> getSubcategoriesByCatId(long categoryId){
        return subcategoryDao.getSubcategoriesByCatId(categoryId);
    }

    public void deleteSubcategory(long id) {
        executorService.submit(() -> {
            subcategoryDao.delete(id);
        });
    }

    public String getSubcategoryNameById(long id){
        Future<String> task = executorService.submit(() -> subcategoryDao.getSubcategoryNameById(id));

        try {
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
