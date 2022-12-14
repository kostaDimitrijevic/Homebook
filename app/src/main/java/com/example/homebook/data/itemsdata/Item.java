package com.example.homebook.data.itemsdata;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.homebook.data.categorydata.Category;
import com.example.homebook.data.subcategorydata.Subcategory;

@Entity(tableName = "items", foreignKeys = {@ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "idC",
        onUpdate = CASCADE,
        onDelete = CASCADE
),
@ForeignKey(
        entity = Subcategory.class,
        parentColumns = "id",
        childColumns = "idS",
        onUpdate = CASCADE,
        onDelete = CASCADE
)},
indices = {@Index(value = {"idC"}), @Index(value = {"idS"})})
public class Item {

    @PrimaryKey(autoGenerate = true)
    long id;

    @NonNull
    String itemName;

    int amount;

    //category id
    Long idC;

    //subcategory id
    Long idS;

    public Item(long id, @NonNull String itemName, Long idC, Long idS, int amount) {
        this.id = id;
        this.itemName = itemName;
        this.idC = idC;
        this.idS = idS;
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

    public Long getIdC() {
        return idC;
    }

    public void setIdC(Long idC) {
        this.idC = idC;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }
}
