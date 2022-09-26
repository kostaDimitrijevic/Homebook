package com.example.homebook.data.analyticsdata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class AnalyticsItemRepository {
    private AnalyticsItemDao analyticsItemDao;
    private ExecutorService executorService;

    @Inject
    public AnalyticsItemRepository(AnalyticsItemDao analyticsItemDao, ExecutorService executorService) {
        this.analyticsItemDao = analyticsItemDao;
        this.executorService = executorService;
    }

    public long insert(String itemName, int amountBought){
        Future<Long> id = executorService.submit(() -> analyticsItemDao.insert(itemName, amountBought));

        try {
            return id.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void update(int amountBough, long id){
        executorService.submit(() -> {
           analyticsItemDao.update(amountBough, id);
        });
    }

    public LiveData<List<AnalyticsItem>> getTop5MaxAmountItems(){
        return analyticsItemDao.getTop5MaxAmountItems();
    }

    public AnalyticsItem getItemByName(String itemName){
        Future<AnalyticsItem> analyticsItemFuture = executorService.submit(() -> analyticsItemDao.getItemByName(itemName));

        try {
            return analyticsItemFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
