package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interesting")
data class InterestingMovieDbo(
    @PrimaryKey
    @ColumnInfo(name = "kinopoisk_id")
    val kinopoiskId: Int
)