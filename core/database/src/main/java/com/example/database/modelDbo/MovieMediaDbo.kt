package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies_media")
data class MovieMediaDbo(
    @PrimaryKey
    @ColumnInfo(name = "kinopoiskId")
    val kinopoiskId: Int,
    @ColumnInfo(name = "name_ru")
    val nameRu: String?,
    @ColumnInfo(name = "name_en")
    val nameEn: String?,
    @ColumnInfo(name = "name_original")
    val nameOriginal: String?,
    @ColumnInfo(name = "countries")
    val countries: List<CountriesDbo>,
    @ColumnInfo(name = "genres")
    val genres: List<GenresDbo>,
    @ColumnInfo(name = "rating_kinopoisk")
    val ratingKinopoisk: String?,
    @ColumnInfo(name = "year")
    val year: String?,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,
    @ColumnInfo(name = "url_preview")
    val posterUrlPreview: String?,
    @ColumnInfo(name = "page")
    var page: Int,
)

