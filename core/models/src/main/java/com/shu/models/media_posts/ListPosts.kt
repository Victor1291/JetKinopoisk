package com.shu.models.media_posts

data class ListPosts(
    val total      : Int?             ,
    val totalPages : Int?             ,
    val items      : List<Post>
)
