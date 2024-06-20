package com.shu.network.models.filters

import com.example.database.modelDbo.FiltersDbo
import com.google.gson.annotations.SerializedName
import com.shu.models.ListFilters

data class ListFiltersDto(
    @SerializedName("genres") val genres: List<GenresDto> = listOf(),
    @SerializedName("countries") val countries: List<CountriesDto> = listOf()
)

fun ListFiltersDto.mapToBd(): FiltersDbo {
    return with(this) {
        FiltersDbo(
            genres = genres.map { it.mapToBd() },
            countries = countries.map { it.mapToBd() },
        )
    }
}

fun ListFiltersDto.mapFromApi(): ListFilters {
    return with(this) {
        ListFilters(
            genres = genres.map { it.mapFrom() },
            countries = countries.map { it.mapFrom() },
        )
    }
}

fun FiltersDbo.mapFromBd(): ListFilters {
    return with(this) {
        ListFilters(
            genres = genres.map { it.mapFromBd() },
            countries = countries.map { it.mapFromBd() },
        )
    }
}