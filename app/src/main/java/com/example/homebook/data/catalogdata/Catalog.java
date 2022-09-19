package com.example.homebook.data.catalogdata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "catalog")
public class Catalog {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String catalogName;

    private int status;

    @NonNull
    private String date;

    public Catalog(long id, @NonNull String catalogName, int status, String date) {
        this.id = id;
        this.catalogName = catalogName;
        this.status = status;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(@NonNull String catalogName) {
        this.catalogName = catalogName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
