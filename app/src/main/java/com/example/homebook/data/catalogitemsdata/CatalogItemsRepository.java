package com.example.homebook.data.catalogitemsdata;

import androidx.lifecycle.LiveData;

import com.example.homebook.data.JoinItemsCatalog;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class CatalogItemsRepository {

    private final CatalogItemsDao catalogItemsDao;
    private final ExecutorService executorService;

    @Inject
    public CatalogItemsRepository(CatalogItemsDao catalogItemsDao, ExecutorService executorService) {
        this.catalogItemsDao = catalogItemsDao;
        this.executorService = executorService;
    }

    public void insert(long idC, long idI, int amount){
        executorService.submit(() -> {
            catalogItemsDao.insert(idC, idI, amount);
        });
    }

    public void deleteCatalogItems(long idC){
        executorService.submit(() -> {
            catalogItemsDao.delete(idC);
        });
    }

    public LiveData<List<CatalogItems>> retrieveItemIdsByCatalog(Long idC){
        return catalogItemsDao.retrieveItemIdsByCatalog(idC);
    }

    public LiveData<List<JoinItemsCatalog>> getItemsForCatalog(long idC){
        return catalogItemsDao.getItemsForCatalog(idC);
    }
}
