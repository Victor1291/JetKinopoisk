package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_countries",
    primaryKeys = ["country","kinopoisk_id"])
data class MovieCountryDbo(
    @ColumnInfo(name= "country")
    val country: String,
    @ColumnInfo(name= "kinopoisk_id")
    val kinopoiskId: Int
)
