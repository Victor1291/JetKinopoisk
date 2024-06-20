package com.shu.models.collections

data class Collections (
    val collectionId    : Int,
    val name            : String?,
    var total           : Int,
    val icon            : Int,
    var checked         : Boolean,
)