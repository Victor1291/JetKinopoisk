package com.shu.network.models

import com.google.gson.annotations.SerializedName
import com.shu.models.Genres
import com.shu.models.ListCinema
import com.shu.network.models.filters.GenresDto

data class ListCinemaDto(

    @SerializedName("total") val total: Int? = null,
    @SerializedName("totalPages" ) val totalPages : Int? = null,
    @SerializedName("items") val items: List<CinemaItemDto> = listOf()

)


fun ListCinemaDto.mapFrom(): ListCinema {
    return with(this) {
        ListCinema(
            total = total,
            totalPages = totalPages,
            items = items.map { it.mapFrom() },
        )
    }
}