package com.shu.network.modelDetail

import com.google.gson.annotations.SerializedName
import com.shu.models.CinemaItem
import com.shu.models.details.Actor
import com.shu.network.models.ColectionsDto
import com.shu.network.models.mapFrom

data class ActorsDto(
    @SerializedName("staffId") var staffId: Int? = null,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("professionText") var professionText: String? = null,
    @SerializedName("professionKey") var professionKey: String? = null,
)

fun ActorsDto.toActor(): Actor {
    return with(this) {
        Actor(
            staffId = staffId,
            nameRu = nameRu,
            nameEn = nameEn,
            description = description,
            posterUrl = posterUrl,
            professionText = professionText,
            professionKey = professionKey,
            )
    }
}