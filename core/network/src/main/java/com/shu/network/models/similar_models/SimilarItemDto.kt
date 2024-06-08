package com.shu.network.models.similar_models

import com.google.gson.annotations.SerializedName
import com.shu.models.similar_models.SimilarItem

data class SimilarItemDto(
    @SerializedName("filmId") var filmId: Int,
    @SerializedName("nameRu") var nameRu: String?,
    @SerializedName("nameEn") var nameEn: String?,
    @SerializedName("nameOriginal") var nameOriginal: String?,
    @SerializedName("posterUrl") var posterUrl: String?,
    @SerializedName("posterUrlPreview") var posterUrlPreview: String?
)

fun SimilarItemDto.toSimilarItem(): SimilarItem {
    return with(this) {
        SimilarItem(
            filmId = filmId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameOriginal,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
        )
    }
}