package com.example.homebook.data.itemsdata;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.homebook.data.categorydata.Category;

@Entity(tableName = "items", foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "idC",
        onUpdate = CASCADE,
        onDelete = CASCADE
))
public class Item {

    @PrimaryKey(autoGenerate = true)
    long id;

    @NonNull
    String itemName;

    long idC;

    public Item(long id, @NonNull String itemName, long idC) {
        this.id = id;
        this.itemName = itemName;
        this.idC = idC;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }
}
