package com.shu.network.models.filters

import com.example.database.modelDbo.CountriesDbo
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

fun CountriesDto.mapToBd(): CountriesDbo {
    return with(this) {
        CountriesDbo(
            country = country,
            id = id
        )
    }
}

fun CountriesDbo.mapFromBd(): Countries {
    return with(this) {
        Countries(
            country = country,
            id = id
        )
    }
}

fun Countries.mapToBd(): CountriesDbo {
    return with(this) {
        CountriesDbo(
            country = country,
            id = id
        )
    }
}