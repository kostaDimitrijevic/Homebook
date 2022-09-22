package com.example.homebook.data.catalogitemsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatalogItemsDao {

    @Query("INSERT INTO catalog_items ('idC', 'idI', 'amount') VALUES(:idC, :idI, :amount)")
    long insert(long idC, long idI, int amount);

    @Query("SELECT idI FROM catalog_items WHERE idC=:idC")
    LiveData<List<Long>> retrieveItemIdsByCatalog(long idC);
}
