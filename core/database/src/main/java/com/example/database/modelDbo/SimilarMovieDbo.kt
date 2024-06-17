package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "similar_movie",
    primaryKeys = ["similar_id","kinopoisk_id"])
data class SimilarMovieDbo(
    @ColumnInfo(name= "similar_id")
    val similarId: Int,
    @ColumnInfo(name= "kinopoisk_id")
    val kinopoiskId: Int
)
