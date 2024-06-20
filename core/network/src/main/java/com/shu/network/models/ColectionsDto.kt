package com.shu.network.models

import com.example.database.modelDbo.CollectionsDbo
import com.google.gson.annotations.SerializedName
import com.shu.models.CinemaItem
import com.shu.network.models.filters.CountriesDto
import com.shu.network.models.filters.GenresDto
import com.shu.network.models.filters.mapFrom
import com.shu.models.collections.Collections

data class ColectionsDto(
    @SerializedName(value = "filmId", alternate = ["kinopoiskId"])
    val kinopoiskId: Int?,
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
    val year: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("posterUrl")
    val posterUrl: String?,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String?,
)

fun ColectionsDto.mapFrom(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = kinopoiskId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = year?.toInt(),
            rating = ratingKinopoisk,
            countries = countries.map { it.mapFrom() },
            genres = genres.map { it.mapFrom() },
            premiereRu = null,
        )
    }
}

fun CollectionsDbo.mapFromBd(): Collections {
    return with(this) {
        Collections(
            collectionId = collectionId,
            name = name,
            total = total,
            icon = icon,
            checked = checked,
        )
    }
}