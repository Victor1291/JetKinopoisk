package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(
    tableName = "movie_genres",
    primaryKeys = ["genre","kinopoisk_id"])
data class MovieGenresDbo(
    @ColumnInfo(name= "genre")
    val genre: String,
    @ColumnInfo(name= "kinopoisk_id")
    val kinopoiskId: Int
)
