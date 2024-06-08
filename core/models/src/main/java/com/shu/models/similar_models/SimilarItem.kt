package com.shu.models.similar_models

data class SimilarItem(
     var filmId: Int,
     var nameRu: String?,
     var nameEn: String?,
     var nameOriginal: String?,
     var posterUrl: String?,
     var posterUrlPreview: String?
)
