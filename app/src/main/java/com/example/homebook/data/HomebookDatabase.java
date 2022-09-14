package com.example.homebook.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class HomebookDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

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
