package com.shu.models.media_posts

data class Post(
    val kinopoiskId : Int?   ,
    val imageUrl    : String?,
    val title       : String?,
    val description : String?,
    val url         : String?,
    val publishedAt : String?
)
