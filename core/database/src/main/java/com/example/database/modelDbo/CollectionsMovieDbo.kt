package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
связующая таблица. collection - movie должно быть уникальным
 */
@Entity(
    tableName = "collections_movie",
    primaryKeys = ["collection_id","kinopoisk_id"]
)
data class CollectionsMovieDbo(
    @ColumnInfo(name= "collection_id")
    val collectionId: Int,
    @ColumnInfo(name= "kinopoisk_id")
    val kinopoiskId: Int
)
