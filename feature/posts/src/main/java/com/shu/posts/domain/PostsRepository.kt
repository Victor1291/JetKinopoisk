package com.shu.posts.domain


import androidx.paging.PagingData
import com.shu.models.ManyScreens
import com.shu.models.media_posts.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(pageNew: Int): Flow<PagingData<Post>>

}