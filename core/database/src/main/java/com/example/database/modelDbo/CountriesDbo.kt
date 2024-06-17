package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountriesDbo(
    @PrimaryKey
    @ColumnInfo(name = "country")
    val country: String,
)
