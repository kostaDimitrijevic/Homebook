package com.example.homebook.data.itemsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("INSERT INTO items ('itemName', 'idC', 'amount') VALUES(:itemName, :idC, :amount)")
    long insert(String itemName, long idC, int amount);

    @Query("SELECT * FROM items WHERE idC=:categoryId")
    LiveData<List<Item>> getAllItemsByCategoryId(long categoryId);

    @Query("DELETE FROM items WHERE id=:id")
    void delete(long id);

    @Query("UPDATE items SET amount=:amount WHERE id=:id")
    void updateAmount(long id, int amount);
}
