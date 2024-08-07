package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenresDbo (
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    @ColumnInfo(name = "name")
    val genre: String,
    )