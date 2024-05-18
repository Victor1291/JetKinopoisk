package com.shu.network.models.filters

import com.google.gson.annotations.SerializedName
import com.shu.models.Genres

data class GenresDto(
    @SerializedName("id") val id: Int,
    @SerializedName("genre") val genre: String
)

fun GenresDto.mapFrom(): Genres {
    return with(this) {
        Genres(
            id = id,
            genre = genre
        )
    }
}