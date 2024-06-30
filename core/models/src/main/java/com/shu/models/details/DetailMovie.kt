package com.shu.models.details

import com.shu.models.Countries
import com.shu.models.Genres

data class DetailMovie(
    val kinopoiskId: Int = 0,
    val kinopoiskHDId: String? = null,
    val imdbId: String? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val coverUrl: String? = null,
    val logoUrl: String? = null,
    val reviewsCount: Int? = null,
    val ratingGoodReview: Double? = null,
    val ratingGoodReviewVoteCount: Int? = null,
    val ratingKinopoisk: Double? = null,
    val ratingKinopoiskVoteCount: Int? = null,
    val ratingImdb: Double? = null,
    val ratingImdbVoteCount: Int? = null,
    val ratingFilmCritics: Double? = null,
    val ratingFilmCriticsVoteCount: Int? = null,
    val ratingAwait: Double? = null,
    val ratingAwaitCount: Int? = null,
    val ratingRfCritics: Double? = null,
    val ratingRfCriticsVoteCount: Int? = null,
    val webUrl: String? = null,
    val year: Int? = null,
    val filmLength: Int? = null,
    val slogan: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val editorAnnotation: String? = null,
    val isTicketsAvailable: Boolean? = null,
    val productionStatus: String? = null,
    val type: String? = null,
    val ratingMpaa: String? = null,
    val ratingAgeLimits: String? = null,
    val hasImax: Boolean? = null,
    val has3D: Boolean? = null,
    val lastSync: String? = null,
    val countries: List<Countries> = emptyList(),
    val genres: List<Genres> = emptyList(),
    val startYear: Int? = null,
    val endYear: Int? = null,
    val serial: Boolean? = null,
    val shortFilm: Boolean? = null,
    val completed: Boolean? = null,
    var favorite: Boolean = false,
    var watched: Boolean = false,
    var seeLater: Boolean = false,
) {
    private val countriesList: String
        get() = countries.joinToString(",") { it.country }
    val genresList: String
        get() = genres.joinToString(",") { it.genre }
    private val rate: String
        get() = ratingAgeLimits?.replace("age", "+") ?: ""
    private val hours = filmLength?.div(60)

    //  private val minute = hours?.let { filmLength?.minus(it.times(60)) }
    private val lenght: String
        get() = "${filmLength?.div(60) ?: ""}:${hours?.let { filmLength?.minus(it.times(60)) } ?: ""}"


    val cityRateFilmLength: String
        get() = "$countriesList $lenght $rate"


}


