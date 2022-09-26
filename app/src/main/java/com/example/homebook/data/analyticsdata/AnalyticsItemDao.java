package com.example.homebook.data.analyticsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AnalyticsItemDao {
    @Query("INSERT INTO analytics_items ('itemName', 'amountBought') VALUES(:itemName, :amountBought)")
    long insert(String itemName, int amountBought);

    @Query("UPDATE analytics_items SET amountBought=amountBought+:amountBought WHERE id=:id")
    void update(int amountBought, long id);

    @Query("SELECT * FROM analytics_items ORDER BY amountBought DESC LIMIT 5")
    LiveData<List<AnalyticsItem>> getTop5MaxAmountItems();

    @Query("SELECT * FROM analytics_items WHERE itemName=:itemName")
    AnalyticsItem getItemByName(String itemName);
}
