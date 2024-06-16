package com.shu.network.models.media_posts

import com.google.gson.annotations.SerializedName
import com.shu.models.media_posts.ListPosts

data class ListPostsDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("totalPages") var totalPages: Int? = null,
    @SerializedName("items") var items: List<PostDto> = listOf()
)

fun ListPostsDto.toListPosts(): ListPosts {
    return with(this) {
        ListPosts(
            total = total,
            totalPages = totalPages,
            items = items.map { it.toPost() },
        )
    }
}