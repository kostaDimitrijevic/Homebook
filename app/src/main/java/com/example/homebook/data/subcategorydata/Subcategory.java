package com.example.homebook.data.subcategorydata;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.homebook.data.categorydata.Category;

@Entity(tableName = "subcategory",
        foreignKeys = @ForeignKey(
            entity = Category.class,
            parentColumns = "id",
            childColumns = "idCat",
            onUpdate = CASCADE,
            onDelete = CASCADE
        ),
        indices = {@Index(value = {"idCat"})}
)
public class Subcategory {

    @PrimaryKey(autoGenerate = true)
    long id;

    long idCat;

    @NonNull
    String subName;

    public Subcategory(long id, long idCat, @NonNull String subName) {
        this.id = id;
        this.idCat = idCat;
        this.subName = subName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCat() {
        return idCat;
    }

    public void setIdC(long idCat) {
        this.idCat = idCat;
    }

    @NonNull
    public String getSubName() {
        return subName;
    }

    public void setSubName(@NonNull String subName) {
        this.subName = subName;
    }
}
