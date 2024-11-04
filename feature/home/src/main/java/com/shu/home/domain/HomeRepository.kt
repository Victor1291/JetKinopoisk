package com.shu.home.domain


import androidx.paging.PagingData
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
import com.shu.models.media_posts.Post
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getAllScreen(): ManyScreens
    suspend fun getAllNewScreen(): ManyScreens

    fun getPosts(): Flow<PagingData<Post>>

}