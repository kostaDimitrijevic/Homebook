package com.example.homebook.data.catalogitemsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.homebook.data.JoinItemsCatalog;

import java.util.List;

@Dao
public interface CatalogItemsDao {

    @Query("INSERT INTO catalog_items ('idC', 'idI', 'amount') VALUES(:idC, :idI, :amount)")
    long insert(long idC, long idI, int amount);

    @Query("SELECT * FROM catalog_items WHERE idC=:idC")
    List<CatalogItems> retrieveItemIdsByCatalog(long idC);

    @Query("SELECT items.itemName, catalog_items.amount FROM items INNER JOIN catalog_items ON items.id=catalog_items.idI WHERE catalog_items.idC=:idC")
    LiveData<List<JoinItemsCatalog>> getItemsForCatalog(long idC);

    @Query("DELETE FROM catalog_items WHERE idC=:idC")
    void delete(long idC);
}
