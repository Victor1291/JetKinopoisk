package com.shu.home.domain


import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts

interface HomeRepository {

    suspend fun getAllScreen(): ManyScreens
    suspend fun getAllNewScreen(): ManyScreens

    suspend fun getPosts(page: Int): ListPosts

}