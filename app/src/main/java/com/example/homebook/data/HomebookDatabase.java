package com.example.homebook.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.homebook.data.categorydata.Category;
import com.example.homebook.data.categorydata.CategoryDao;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.data.itemsdata.ItemDao;

@Database(entities = {Category.class, Item.class}, version = 1, exportSchema = false)
public abstract class HomebookDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract ItemDao itemDao();

    private static volatile HomebookDatabase instance = null;
    private static final String DATABASE_NAME = "homebook.db";

    public static HomebookDatabase getInstance(Context context){

        if(instance == null){
            synchronized (HomebookDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), HomebookDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration().createFromAsset("homebook.db").build();
                }
            }
        }

        return instance;
    }
}
