package com.foodsoul.test.database.image_search.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggestions_table")
data class Suggestion(
    @PrimaryKey(autoGenerate = false)
    val text: String
)