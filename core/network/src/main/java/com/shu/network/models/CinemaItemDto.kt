package com.shu.network.models

import com.google.gson.annotations.SerializedName
import com.shu.models.CinemaItem
import com.shu.network.models.filters.CountriesDto
import com.shu.network.models.filters.GenresDto
import com.shu.network.models.filters.mapFrom

data class CinemaItemDto(
    @SerializedName("filmId")
    val kinopoiskId: Int?,
    @SerializedName("filmId")
    var filmId: Int?,
    @SerializedName("nameRu")
    val nameRu: String?,
    @SerializedName("nameEn")
    val nameEn: String?,
    @SerializedName("posterUrl")
    val posterUrl: String?,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String?,
    @SerializedName("filmLength")
    val filmLength: String?,
    @SerializedName("year")
    val yearInt: Int?,
    @SerializedName("year")
    var year: String? = null,
    @SerializedName("rating")
    var rating: String?,
    @SerializedName("countries")
    val countries: List<CountriesDto>,
    @SerializedName("genres")
    val genres: List<GenresDto>,
    @SerializedName("imdbId")
    val imdbId: String?
)

fun CinemaItemDto.mapFrom(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = kinopoiskId,
            filmId = filmId,
            nameRu = nameRu,
            nameEn = nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            filmLength = filmLength,
            yearInt = yearInt,
            year = year,
            rating = rating,
            countries = countries.map { it.mapFrom() },
            genres = genres.map { it.mapFrom() },
            imdbId = imdbId,
        )
    }
}
