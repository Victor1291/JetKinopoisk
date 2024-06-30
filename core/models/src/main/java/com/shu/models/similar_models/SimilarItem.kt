package com.shu.models.similar_models

import com.shu.models.CinemaItem

data class SimilarItem(
    var filmId: Int,
    var nameRu: String?,
    var nameEn: String?,
    var nameOriginal: String?,
    var posterUrl: String?,
    var posterUrlPreview: String?
)


fun SimilarItem.toCinemaItem(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = filmId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameOriginal,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = 0,
            rating = "",
            countries = emptyList(),
            genres = emptyList(),
            premiereRu = ""
        )
    }
}
