package com.shu.models

data class CinemaItem(

    val kinopoiskId: Int?,
    val filmId: Int?,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val filmLength: String?,
    val yearInt: Int?,
    var year: String? = null,
    var rating: String?,
    val countries: List<Countries>,
    val genres: List<Genres>,
    val imdbId: String?

)