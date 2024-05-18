package com.shu.network.models.filters

import com.google.gson.annotations.SerializedName
import com.shu.models.Countries

data class CountriesDto(

    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String

)

fun CountriesDto.mapFrom(): Countries {
    return with(this) {
        Countries(
            id = id,
            country = country
        )
    }
}