package com.shu.network.models.similar_models

import com.google.gson.annotations.SerializedName
import com.shu.models.similar_models.ListSimilar

data class ListSimilarDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("items") var items: List<SimilarItemDto> = listOf(),

    )

fun ListSimilarDto.toListSimilar(): ListSimilar {
    return with(this) {
        ListSimilar(
            total = total,
            items = items.map { it.toSimilarItem() }
        )
    }
}