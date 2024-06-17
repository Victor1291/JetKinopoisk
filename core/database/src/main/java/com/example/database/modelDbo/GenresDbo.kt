package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenresDbo (
    @PrimaryKey
    @ColumnInfo(name = "name")
    val genre: String,
    )