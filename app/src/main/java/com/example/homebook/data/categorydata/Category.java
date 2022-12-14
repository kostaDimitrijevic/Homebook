package com.example.homebook.data.categorydata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "category", indices = {@Index(value = {"categoryName"}, unique = true)})
public class Category {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String categoryName;

    public Category(long id, @NonNull String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
