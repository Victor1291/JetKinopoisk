package com.shu.network.modelDetail

import com.google.gson.annotations.SerializedName
import com.shu.models.details.DetailMovie
import com.shu.network.models.filters.CountriesDto
import com.shu.network.models.filters.GenresDto
import com.shu.network.models.filters.mapFrom

data class DetailMovieDto(
    @SerializedName("kinopoiskId") var kinopoiskId: Int? = null,
    @SerializedName("kinopoiskHDId") var kinopoiskHDId: String? = null,
    @SerializedName("imdbId") var imdbId: String? = null,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("nameOriginal") var nameOriginal: String? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("posterUrlPreview") var posterUrlPreview: String? = null,
    @SerializedName("coverUrl") var coverUrl: String? = null,
    @SerializedName("logoUrl") var logoUrl: String? = null,
    @SerializedName("reviewsCount") var reviewsCount: Int? = null,
    @SerializedName("ratingGoodReview") var ratingGoodReview: Double? = null,
    @SerializedName("ratingGoodReviewVoteCount") var ratingGoodReviewVoteCount: Int? = null,
    @SerializedName("ratingKinopoisk") var ratingKinopoisk: Double? = null,
    @SerializedName("ratingKinopoiskVoteCount") var ratingKinopoiskVoteCount: Int? = null,
    @SerializedName("ratingImdb") var ratingImdb: Double? = null,
    @SerializedName("ratingImdbVoteCount") var ratingImdbVoteCount: Int? = null,
    @SerializedName("ratingFilmCritics") var ratingFilmCritics: Double? = null,
    @SerializedName("ratingFilmCriticsVoteCount") var ratingFilmCriticsVoteCount: Int? = null,
    @SerializedName("ratingAwait") var ratingAwait: Double? = null,
    @SerializedName("ratingAwaitCount") var ratingAwaitCount: Int? = null,
    @SerializedName("ratingRfCritics") var ratingRfCritics: Double? = null,
    @SerializedName("ratingRfCriticsVoteCount") var ratingRfCriticsVoteCount: Int? = null,
    @SerializedName("webUrl") var webUrl: String? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("filmLength") var filmLength: Int? = null,
    @SerializedName("slogan") var slogan: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("shortDescription") var shortDescription: String? = null,
    @SerializedName("editorAnnotation") var editorAnnotation: String? = null,
    @SerializedName("isTicketsAvailable") var isTicketsAvailable: Boolean? = null,
    @SerializedName("productionStatus") var productionStatus: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("ratingMpaa") var ratingMpaa: String? = null,
    @SerializedName("ratingAgeLimits") var ratingAgeLimits: String? = null,
    @SerializedName("hasImax") var hasImax: Boolean? = null,
    @SerializedName("has3D") var has3D: Boolean? = null,
    @SerializedName("lastSync") var lastSync: String? = null,
    @SerializedName("countries") var countries: List<CountriesDto> = listOf(),
    @SerializedName("genres") var genres: List<GenresDto> = listOf(),
    @SerializedName("startYear") var startYear: Int? = null,
    @SerializedName("endYear") var endYear: Int? = null,
    @SerializedName("serial") var serial: Boolean? = null,
    @SerializedName("shortFilm") var shortFilm: Boolean? = null,
    @SerializedName("completed") var completed: Boolean? = null

)

fun DetailMovieDto.mapFrom(): DetailMovie {
    return with(this) {

        DetailMovie(
            kinopoiskId = kinopoiskId,
            kinopoiskHDId = kinopoiskHDId,
            imdbId = imdbId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameOriginal,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            coverUrl = coverUrl,
            logoUrl = logoUrl,
            reviewsCount = reviewsCount,
            ratingGoodReview = ratingGoodReview,
            ratingGoodReviewVoteCount = ratingGoodReviewVoteCount,
            ratingKinopoisk = ratingKinopoisk,
            ratingKinopoiskVoteCount = ratingKinopoiskVoteCount,
            ratingImdb = ratingImdb,
            ratingImdbVoteCount = ratingImdbVoteCount,
            ratingFilmCritics = ratingFilmCritics,
            ratingFilmCriticsVoteCount = ratingFilmCriticsVoteCount,
            ratingAwait = ratingAwait,
            ratingAwaitCount = ratingAwaitCount,
            ratingRfCritics = ratingRfCritics,
            ratingRfCriticsVoteCount = ratingRfCriticsVoteCount,
            webUrl = webUrl,
            year = year,
            filmLength = filmLength,
            slogan = slogan,
            description = description,
            shortDescription = shortDescription,
            editorAnnotation = editorAnnotation,
            isTicketsAvailable = isTicketsAvailable,
            productionStatus = productionStatus,
            type = type,
            ratingMpaa = ratingMpaa,
            ratingAgeLimits = ratingAgeLimits,
            hasImax = hasImax,
            has3D = has3D,
            lastSync = lastSync,
            countries = countries.map { it.mapFrom() },
            genres = genres.map { it.mapFrom() },
            startYear = startYear,
            endYear = endYear,
            serial = serial,
            shortFilm = shortFilm,
            completed = completed,
        )
    }
}


