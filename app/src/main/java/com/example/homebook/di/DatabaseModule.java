package com.example.homebook.di;

import android.content.Context;

import com.example.homebook.data.catalogdata.CatalogDao;
import com.example.homebook.data.catalogitemsdata.CatalogItemsDao;
import com.example.homebook.data.categorydata.CategoryDao;
import com.example.homebook.data.HomebookDatabase;
import com.example.homebook.data.itemsdata.ItemDao;
import com.example.homebook.data.subcategorydata.SubcategoryDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface DatabaseModule {

    @Provides
    static CategoryDao provideCategoryDao(@ApplicationContext Context context){
        return HomebookDatabase.getInstance(context).categoryDao();
    }

    @Provides
    static ItemDao provideItemDao(@ApplicationContext Context context){
        return HomebookDatabase.getInstance(context).itemDao();
    }

    @Provides
    static SubcategoryDao provideSubcategoryDao(@ApplicationContext Context context){
        return HomebookDatabase.getInstance(context).subcategoryDao();
    }

    @Provides
    static CatalogDao provideCatalogDao(@ApplicationContext Context context){
        return HomebookDatabase.getInstance(context).catalogDao();
    }

    @Provides
    static CatalogItemsDao provideCatalogItemsDao(@ApplicationContext Context context){
        return HomebookDatabase.getInstance(context).catalogItemsDao();
    }
}
