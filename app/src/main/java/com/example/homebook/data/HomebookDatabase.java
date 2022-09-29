package com.example.homebook.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.homebook.data.analyticsdata.AnalyticsItem;
import com.example.homebook.data.analyticsdata.AnalyticsItemDao;
import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.catalogdata.CatalogDao;
import com.example.homebook.data.catalogitemsdata.CatalogItems;
import com.example.homebook.data.catalogitemsdata.CatalogItemsDao;
import com.example.homebook.data.categorydata.Category;
import com.example.homebook.data.categorydata.CategoryDao;
import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.data.frienddata.FriendDao;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.data.itemsdata.ItemDao;
import com.example.homebook.data.subcategorydata.Subcategory;
import com.example.homebook.data.subcategorydata.SubcategoryDao;

@Database(entities = {Category.class, Item.class, Subcategory.class, Catalog.class, CatalogItems.class, AnalyticsItem.class, Friend.class}, version = 1, exportSchema = false)
public abstract class HomebookDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract ItemDao itemDao();
    public abstract SubcategoryDao subcategoryDao();
    public abstract CatalogDao catalogDao();
    public abstract CatalogItemsDao catalogItemsDao();
    public abstract AnalyticsItemDao analyticsItemDao();
    public abstract FriendDao friendDao();

    private static volatile HomebookDatabase instance = null;
    private static final String DATABASE_NAME = "homebook.db";

    public static HomebookDatabase getInstance(Context context){

        if(instance == null){
            synchronized (HomebookDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), HomebookDatabase.class, DATABASE_NAME).createFromAsset("homebook.db").build();
                }
            }
        }

        return instance;
    }

}
