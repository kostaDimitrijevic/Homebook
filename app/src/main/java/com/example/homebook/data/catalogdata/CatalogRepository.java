package com.example.homebook.data.catalogdata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class CatalogRepository {
    private final CatalogDao catalogDao;
    private final ExecutorService executorService;

    @Inject
    public CatalogRepository(CatalogDao catalogDao, ExecutorService executorService) {
        this.catalogDao = catalogDao;
        this.executorService = executorService;
    }

    public long insert(Catalog catalog){
        Future<Long> task = executorService.submit(() -> catalogDao.insert(catalog.getCatalogName(), catalog.getStatus(), catalog.getUser(), catalog.getDate()));

        try {
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void delete(long id){
        executorService.submit(() -> {
            catalogDao.delete(id);
        });
    }

    public LiveData<List<Catalog>> getAllCatalogs(){
        return catalogDao.getAllCatalogs();
    }

}
