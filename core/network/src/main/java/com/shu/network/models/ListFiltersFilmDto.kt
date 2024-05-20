package com.shu.network.models

import com.google.gson.annotations.SerializedName
import com.shu.models.ListCinema

data class ListFiltersFilmDto(

    @SerializedName("total") var total: Int? = null,
    @SerializedName("totalPages") var totalPages: Int? = null,
    @SerializedName("items") var items: List<FiltersFilmDto> = listOf()

)


fun ListFiltersFilmDto.mapFrom(): ListCinema {
    return with(this) {
        ListCinema(
            total = total,
            totalPages = totalPages,
            items = items.map { it.mapFrom() },
        )
    }
}