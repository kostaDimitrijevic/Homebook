package com.example.homebook.data.analyticsdata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "analytics_items")
public class AnalyticsItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String itemName;

    @NonNull
    private Integer amountBought;

    public AnalyticsItem(long id, @NonNull String itemName, @NonNull Integer amountBought) {
        this.id = id;
        this.itemName = itemName;
        this.amountBought = amountBought;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public Integer getAmountBought() {
        return amountBought;
    }

    public void setAmountBought(@NonNull Integer amountBought) {
        this.amountBought = amountBought;
    }
}
