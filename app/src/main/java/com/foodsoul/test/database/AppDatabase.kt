package com.foodsoul.test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foodsoul.test.database.image_search.dao.SuggestionsDao
import com.foodsoul.test.database.image_search.model.Suggestion

@Database(entities = [Suggestion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suggestionsDao(): SuggestionsDao
}