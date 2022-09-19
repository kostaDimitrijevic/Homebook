package com.example.homebook.data.catalogitemsdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatalogItemsDao {

    @Query("INSERT INTO catalog_items ('idC', 'idI') VALUES(:idC, :idI)")
    long insert(long idC, long idI);

    @Query("SELECT idI FROM catalog_items WHERE idC=:idC")
    LiveData<List<Long>> retrieveItemIdsByCatalog(long idC);
}
