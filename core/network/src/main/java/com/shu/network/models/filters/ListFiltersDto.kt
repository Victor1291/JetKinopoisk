package com.shu.network.models.filters

import com.google.gson.annotations.SerializedName

data class ListFiltersDto(
    @SerializedName("genres") val genres: List<GenresDto> = listOf(),
    @SerializedName("countries") val countries: List<CountriesDto> = listOf()
)

