package com.shu.network.models_person

import com.google.gson.annotations.SerializedName
import com.shu.models.detail_person.ListSearchPerson

data class ListSearchPersonDto(
    @SerializedName("total")
    val total: Int,
    @SerializedName("items")
    val items: List<PersonDto>
)

fun ListSearchPersonDto.toListSearchPerson(): ListSearchPerson {
    return with(this) {
        ListSearchPerson(
           total = total,
            items = items.map { it.toSearchPerson() }
        )
    }
}