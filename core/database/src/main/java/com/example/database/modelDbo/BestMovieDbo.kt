package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(
tableName = "best_movie",
primaryKeys = ["best_id","kinopoisk_id"])
data class BestMovieDbo(
    @ColumnInfo(name= "best_id")
    val bestId: Int,
    @ColumnInfo(name= "kinopoisk_id")
    val kinopoiskId: Int
)
