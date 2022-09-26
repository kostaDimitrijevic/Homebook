package com.example.homebook.data.catalogdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatalogDao {

    @Query("INSERT INTO catalog ('catalogName', 'status', 'user', 'date') VALUES(:catalogName, :status, :user, :date)")
    long insert(String catalogName, int status, String user, String date);

    @Query("DELETE FROM catalog WHERE id=:id")
    void delete(long id);

    @Query("SELECT * FROM catalog")
    LiveData<List<Catalog>> getAllCatalogs();

    @Query("UPDATE catalog SET status=:stat WHERE id=:idC")
    void updateStatus(int stat, long idC);

    @Query("UPDATE catalog SET date=:date WHERE id=:idC")
    void updateDate(String date, long idC);
}
