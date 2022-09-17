package com.example.homebook.data.catalogdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatalogDao {

    @Query("INSERT INTO catalog ('catalogName', 'status', 'date') VALUES(:catalogName, :status, :date)")
    long insert(String catalogName, int status, String date);

    @Query("DELETE FROM catalog WHERE id=:id")
    void delete(long id);

    @Query("SELECT * FROM catalog")
    LiveData<List<Catalog>> getAllCatalogs();
}
