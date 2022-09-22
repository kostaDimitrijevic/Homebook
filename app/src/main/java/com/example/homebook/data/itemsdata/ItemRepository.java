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

    public void insert(String itemName, long idC, Long idS, int amount){

        executorService.submit(() -> {
            itemDao.insert(itemName, idC, idS, amount);
        });
    }

    public void delete(long id){
        executorService.submit(() ->{
            itemDao.delete(id);
        });
    }

    public LiveData<List<Item>> getAllItemsByCategoryId(long categoryId){
        return itemDao.getAllItemsByCategoryId(categoryId);
    }

    public LiveData<List<Item>> getAllItemsBySubcategoryId(long subcategoryId){
        return itemDao.getAllItemsBySubcategoryId(subcategoryId);
    }

    public LiveData<List<Item>> getAllItemsWithAmountZero(){
        return itemDao.getAllItemsWithAmountZero();
    }

    public void updateAmount(long id, int amount){
        executorService.submit(() -> {
            itemDao.updateAmount(id, amount);
        });
    }
}
