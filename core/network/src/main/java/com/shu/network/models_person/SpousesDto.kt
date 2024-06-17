package com.shu.network.models_person

data class SpousesDto (
    val personId: Int?,
    val name: String?,
    val divorced: Boolean?,
    val divorcedReason: String?,
    val sex: String?,
    val children: Int?,
    val webUrl: String?,
    val relation: String?,
)
