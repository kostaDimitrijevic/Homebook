package com.example.homebook.data.itemsdata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class ItemRepository {

    private final ItemDao itemDao;
    private final ExecutorService executorService;

    @Inject
    public ItemRepository(ItemDao itemDao, ExecutorService executorService) {
        this.itemDao = itemDao;
        this.executorService = executorService;
    }

    public long insert(String itemName, long idC, Long idS, int amount){

        Future<Long> future =  executorService.submit(() -> itemDao.insert(itemName, idC, idS, amount));

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void delete(long id){
        executorService.submit(() ->{
            itemDao.delete(id);
        });
    }

    public Item getItemById(long id){
        Future<Item> item = executorService.submit(() -> itemDao.getItemById(id));

        try {
            return item.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
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

    public LiveData<List<Item>> getAllItemsWithAmountNotZero(){
        return itemDao.getAllItemsWithAmountNotZero();
    }

    public void updateAmount(long id, int amount){
        executorService.submit(() -> {
            itemDao.updateAmount(id, amount);
        });
    }
}
