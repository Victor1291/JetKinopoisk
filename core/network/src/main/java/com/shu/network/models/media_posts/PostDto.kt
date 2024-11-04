package com.shu.network.models.media_posts

import com.example.database.modelDbo.PostDbo
import com.google.gson.annotations.SerializedName
import com.shu.models.media_posts.Post

data class PostDto(
    @SerializedName("kinopoiskId") var kinopoiskId: Int? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null
)

fun PostDto.toPost(): Post {
    return with(this) {
        Post(
            kinopoiskId = kinopoiskId,
            imageUrl = imageUrl,
            title = title,
            description = description,
            url = url,
            publishedAt = publishedAt,
            page = 1
        )
    }
}

fun PostDto.apiBd(page: Int): PostDbo {
    return with(this) {
        PostDbo(
            kinopoiskId = kinopoiskId ?: 0,
            imageUrl = imageUrl,
            title = title,
            description = description,
            url = url,
            publishedAt = publishedAt,
            page = page
        )
    }
}

fun PostDbo.bdUi(): Post {
    return with(this) {
        Post(
            kinopoiskId = kinopoiskId,
            imageUrl = imageUrl,
            title = title,
            description = description,
            url = url,
            publishedAt = publishedAt,
            page = page
        )
    }
}