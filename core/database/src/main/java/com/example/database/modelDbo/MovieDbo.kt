package com.example.database.modelDbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieDbo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "kinopoisk_id")
    val kinopoiskId: Int?,
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
    @ColumnInfo(name = "watched")
    val ratingKinopoisk: String?,
    @ColumnInfo(name = "see_later")
    val ratingImbd: String?,
    @ColumnInfo(name = "year")
    val year: String?,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,
    @ColumnInfo(name = "url_preview")
    val posterUrlPreview: String?,
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,
)
