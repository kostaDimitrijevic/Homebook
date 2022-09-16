package com.example.homebook.data.itemsdata;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.homebook.data.categorydata.Category;

@Entity(tableName = "items", foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "idC",
        onUpdate = CASCADE,
        onDelete = CASCADE
),
indices = {@Index(value = {"idC"})})
public class Item {

    @PrimaryKey(autoGenerate = true)
    long id;

    @NonNull
    String itemName;

    int amount;

    long idC;

    public Item(long id, @NonNull String itemName, long idC, int amount) {
        this.id = id;
        this.itemName = itemName;
        this.idC = idC;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
