package com.foodsoul.test.di.module

import androidx.room.Room
import com.foodsoul.test.App.Companion.appContext
import com.foodsoul.test.database.AppDatabase
import com.foodsoul.test.database.image_search.dao.SuggestionsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSuggestionsDao(appDatabase: AppDatabase): SuggestionsDao {
        return appDatabase.suggestionsDao()
    }

}