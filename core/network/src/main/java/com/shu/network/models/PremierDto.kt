package com.shu.network.models

import com.google.gson.annotations.SerializedName
import com.shu.models.CinemaItem
import com.shu.network.models.filters.CountriesDto
import com.shu.network.models.filters.GenresDto
import com.shu.network.models.filters.mapFrom

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
