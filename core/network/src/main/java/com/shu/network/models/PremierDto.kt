package com.shu.network.models

import com.example.database.modelDbo.MovieMediaDbo
import com.google.gson.annotations.SerializedName
import com.shu.models.CinemaItem
import com.shu.network.models.filters.CountriesDto
import com.shu.network.models.filters.GenresDto
import com.shu.network.models.filters.mapFrom
import com.shu.network.models.filters.mapFromBd
import com.shu.network.models.filters.mapToBd

data class PremierDto(
    @SerializedName("kinopoiskId")
    val kinopoiskId: Int?,
    @SerializedName("nameRu")
    val nameRu: String?,
    @SerializedName("nameEn")
    val nameEn: String?,
    @SerializedName("year")
    var year: Int?,
    @SerializedName("posterUrl")
    val posterUrl: String?,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String?,
    @SerializedName("countries")
    val countries: List<CountriesDto>,
    @SerializedName("genres")
    val genres: List<GenresDto>,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("premiereRu")
    val premiereRu: String?,
)

fun PremierDto.mapFrom(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = kinopoiskId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = year,
            rating = null,
            countries = countries.map { it.mapFrom() },
            genres = genres.map { it.mapFrom() },
            premiereRu = premiereRu,
        )
    }
}

fun CinemaItem.mapFromApiToBd(page: Int): MovieMediaDbo {
    return with(this) {
        MovieMediaDbo(
            kinopoiskId = kinopoiskId ?: 0,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = year.toString(),
            ratingKinopoisk = rating,
            countries = countries.map { it.mapToBd() },
            genres = genres.map { it.mapToBd() },
            page = page,
        )
    }
}

fun MovieMediaDbo.mapFromBd(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = kinopoiskId ?: 0,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = year?.toInt() ?: 10,
            rating = ratingKinopoisk,
            countries = countries.map { it.mapFromBd() },
            genres = genres.map { it.mapFromBd() },
            premiereRu = null
        )
    }
}