package com.example.homebook.data.catalogitemsdata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

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

    public LiveData<List<Long>> retrieveItemIdsByCatalog(long idC){
        return catalogItemsDao.retrieveItemIdsByCatalog(idC);
    }
}
