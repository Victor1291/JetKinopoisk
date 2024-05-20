package com.shu.network.models

import com.google.gson.annotations.SerializedName
import com.shu.models.ListCinema

data class ListPremiersDto(

    @SerializedName("total") var total: Int? = null,
    @SerializedName("items") var items: List<PremierDto> = listOf()

)


fun ListPremiersDto.mapFrom(): ListCinema {
    return with(this) {
        ListCinema(
            total = total,
            totalPages = null,
            items = items.map { it.mapFrom() },
        )
    }
}