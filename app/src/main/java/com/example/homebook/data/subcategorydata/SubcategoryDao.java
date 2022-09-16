package com.example.homebook.data.subcategorydata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SubcategoryDao {

    @Query("INSERT INTO subcategory ('subName', 'idCat') VALUES(:subName, :idCat)")
    long insert (@NonNull String subName, long idCat);

    @Query("SELECT * FROM subcategory WHERE idCat = :categoryId")
    LiveData<List<Subcategory>> getSubcategoriesByCatId(long categoryId);
}
