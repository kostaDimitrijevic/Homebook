package com.example.homebook.data.catalogitemsdata;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.itemsdata.Item;

@Entity(tableName = "catalog_items", foreignKeys = {@ForeignKey(
        entity = Catalog.class,
        parentColumns = "id",
        childColumns = "idC",
        onUpdate = CASCADE,
        onDelete = CASCADE
), @ForeignKey(
        entity = Item.class,
        parentColumns = "id",
        childColumns = "idI",
        onUpdate = CASCADE,
        onDelete = CASCADE
)}, indices = {@Index(value = {"idC"}), @Index(value = {"idI"})})
public class CatalogItems {

    @PrimaryKey(autoGenerate = true)
    long id;

    //Catalog
    long idC;

    //Item
    long idI;

    int amount;

    public CatalogItems(long id, long idC, long idI, int amount) {
        this.id = id;
        this.idC = idC;
        this.idI = idI;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    public long getIdI() {
        return idI;
    }

    public void setIdI(long idI) {
        this.idI = idI;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
