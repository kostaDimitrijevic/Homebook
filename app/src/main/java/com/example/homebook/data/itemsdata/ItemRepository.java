package com.example.homebook.data.itemsdata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class ItemRepository {

    private final ItemDao itemDao;
    private final ExecutorService executorService;

    @Inject
    public ItemRepository(ItemDao itemDao, ExecutorService executorService) {
        this.itemDao = itemDao;
        this.executorService = executorService;
    }

    public void insert(Item item){

        executorService.submit(() -> {
            itemDao.insert(item);
        });
    }

    public LiveData<List<Item>> getAllItemsByCategoryId(long categoryId){
        return itemDao.getAllItemsByCategoryId(categoryId);
    }
}
