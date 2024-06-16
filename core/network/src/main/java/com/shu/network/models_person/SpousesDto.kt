package com.shu.network.models_person

import com.shu.models.detail_person.Spouses

data class SpousesDto(
    val personId: Int?,
    val name: String?,
    val divorced: Boolean?,
    val divorcedReason: String?,
    val sex: String?,
    val children: Int?,
    val webUrl: String?,
    val relation: String?,
)

fun SpousesDto.toSpouses(): Spouses {
    return with(this) {
        Spouses(
            personId = personId,
            name = name,
            divorced = divorced,
            divorcedReason = divorcedReason,
            sex = sex,
            children = children,
            webUrl = webUrl,
            relation = relation,
        )
    }
}