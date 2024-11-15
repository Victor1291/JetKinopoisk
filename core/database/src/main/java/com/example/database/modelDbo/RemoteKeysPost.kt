package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_key")
data class RemoteKeysPost(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "kinopoiskId")
    val kinopoiskId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
)