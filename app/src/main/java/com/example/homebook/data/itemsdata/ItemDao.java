package com.example.homebook.data.itemsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("INSERT INTO items ('itemName', 'idC', 'idS', 'amount') VALUES(:itemName, :idC, :idS, :amount)")
    long insert(String itemName, Long idC, Long idS, int amount);

    @Query("SELECT * FROM items WHERE idC=:categoryId")
    LiveData<List<Item>> getAllItemsByCategoryId(long categoryId);

    @Query("SELECT * FROM items WHERE idS=:subcategoryId")
    LiveData<List<Item>> getAllItemsBySubcategoryId(long subcategoryId);

    @Query("SELECT * FROM items WHERE amount=0")
    LiveData<List<Item>> getAllItemsWithAmountZero();

    @Query("SELECT * FROM items WHERE amount!=0")
    LiveData<List<Item>> getAllItemsWithAmountNotZero();

    @Query("DELETE FROM items WHERE id=:id")
    void delete(long id);

    @Query("SELECT * FROM items WHERE id=:id")
    Item getItemById(long id);

    @Query("UPDATE items SET amount=:amount WHERE id=:id")
    void updateAmount(long id, int amount);

    @Query("UPDATE items SET amount=amount+:amount WHERE id=:id")
    void updateAddToAmount(long id, int amount);
}
