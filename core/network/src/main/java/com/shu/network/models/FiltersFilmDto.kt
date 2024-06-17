package com.shu.network.models

import com.google.gson.annotations.SerializedName
import com.shu.models.CinemaItem
import com.shu.network.models.filters.CountriesDto
import com.shu.network.models.filters.GenresDto
import com.shu.network.models.filters.mapFrom

data class FiltersFilmDto(
    @SerializedName(value = "filmId", alternate = ["kinopoiskId"])
    val kinopoiskId: Int?,
    @SerializedName(value = "imdbId")
    val imdbId: String?,
    @SerializedName("nameRu")
    val nameRu: String?,
    @SerializedName("nameEn")
    val nameEn: String?,
    @SerializedName("nameOriginal")
    val nameOriginal: String?,
    @SerializedName("countries")
    val countries: List<CountriesDto>,
    @SerializedName("genres")
    val genres: List<GenresDto>,
    @SerializedName("ratingKinopoisk")
    val ratingKinopoisk: String?,
    @SerializedName("ratingImbd")
    val ratingImbd: String?,
    @SerializedName("year")
    var year: Int?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("posterUrl")
    val posterUrl: String?,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String?,
)

fun FiltersFilmDto.mapFrom(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = kinopoiskId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameOriginal,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = year,
            rating = ratingKinopoisk,
            countries = countries.map { it.mapFrom() },
            genres = genres.map { it.mapFrom() },
            premiereRu = null,
        )
    }
}
