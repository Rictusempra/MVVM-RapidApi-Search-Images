package com.foodsoul.test.database.image_search.dao

import androidx.room.*
import com.foodsoul.test.database.image_search.model.Suggestion

@Dao
interface SuggestionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: Suggestion)

    @Query("SELECT * FROM suggestions_table")
    fun getAllItems(): List<Suggestion>

    @Update
    fun updateItem(item: Suggestion)

    @Delete
    fun deleteItem(item: Suggestion)

}