package com.example.homebook.data.itemsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    long insert(Item item);

    @Query("SELECT * FROM items WHERE idC=:categoryId")
    LiveData<List<Item>> getAllItemsByCategoryId(long categoryId);
}
