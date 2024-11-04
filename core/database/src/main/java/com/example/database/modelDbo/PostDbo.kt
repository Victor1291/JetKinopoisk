package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class PostDbo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("kinopoiskId") var kinopoiskId: Int,
    @ColumnInfo("imageUrl") var imageUrl: String?,
    @ColumnInfo("title") var title: String?,
    @ColumnInfo("description") var description: String?,
    @ColumnInfo("url") var url: String? ,
    @ColumnInfo("publishedAt") var publishedAt: String?,
    @ColumnInfo(name = "page")
    var page: Int,
)
