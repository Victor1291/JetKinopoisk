package com.shu.models.similar_models

data class ListSimilar(
    val total: Int? = null,
    val items: List<SimilarItem> = listOf(),
    )
