package com.example.homebook.data.categorydata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    long insert(Category category);

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT id FROM category WHERE categoryName=:category")
    long getCategoryIdByName(String category);
}
