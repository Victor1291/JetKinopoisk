package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
таблица с коллекциями.
 */
@Entity(tableName = "collections")
data class CollectionsDbo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collection_id")
    var collectionId: Int = 0,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "total")
    var total: Int = 0,
    @ColumnInfo(name = "icon")
    var icon: Int = 1,
    @ColumnInfo(name = "checked")
    var checked: Boolean,
)

