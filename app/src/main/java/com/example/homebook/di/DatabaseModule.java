package com.example.homebook.di;

import android.content.Context;

import com.example.homebook.data.CategoryDao;
import com.example.homebook.data.HomebookDatabase;

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
}
