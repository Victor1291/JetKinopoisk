package com.shu.models.details

import com.shu.models.CinemaItem
import com.shu.models.Countries
import com.shu.models.Genres

data class DetailMovie(
    val kinopoiskId: Int?,
    val kinopoiskHDId: String?,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val coverUrl: String?,
    val logoUrl: String?,
    val reviewsCount: Int?,
    val ratingGoodReview: Double?,
    val ratingGoodReviewVoteCount: Int?,
    val ratingKinopoisk: Double?,
    val ratingKinopoiskVoteCount: Int?,
    val ratingImdb: Double?,
    val ratingImdbVoteCount: Int?,
    val ratingFilmCritics: Double?,
    val ratingFilmCriticsVoteCount: Int?,
    val ratingAwait: Double?,
    val ratingAwaitCount: Int?,
    val ratingRfCritics: Double?,
    val ratingRfCriticsVoteCount: Int?,
    val webUrl: String?,
    val year: Int?,
    val filmLength: Int?,
    val slogan: String?,
    val description: String?,
    val shortDescription: String?,
    val editorAnnotation: String?,
    val isTicketsAvailable: Boolean?,
    val productionStatus: String?,
    val type: String?,
    val ratingMpaa: String?,
    val ratingAgeLimits: String?,
    val hasImax: Boolean?,
    val has3D: Boolean?,
    val lastSync: String?,
    val countries: List<Countries>,
    val genres: List<Genres>,
    val startYear: Int?,
    val endYear: Int?,
    val serial: Boolean?,
    val shortFilm: Boolean?,
    val completed: Boolean?,
) {
    val countriesList: String
        get() = countries.joinToString(","){it.country}
    val genresList: String
        get() = genres.joinToString(","){it.genre}
    private val rate : String
        get() = ratingAgeLimits?.replace("age", "+") ?: ""
    private val hours = filmLength?.div(60)
    private val minute = hours?.let { filmLength?.minus(it.times(60)) }
    val lenght : String
        get() = "$hours:$minute"


    val cityRateFilmLength : String
        get() = "$countriesList $lenght $rate"


}


