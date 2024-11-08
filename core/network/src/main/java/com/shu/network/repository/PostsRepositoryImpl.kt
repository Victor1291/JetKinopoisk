package com.shu.network.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.database.MovieDao
import com.example.database.MovieDatabase
import com.shu.models.media_posts.Post
import com.shu.network.ServiceMovieApi
import com.shu.network.models.media_posts.bdUi
import com.shu.network.paging.PostRemoteMediator
import com.shu.posts.domain.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
    private val database: MovieDatabase,
) : PostsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPosts(pageNew: Int): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 15, prefetchDistance = 4),
            pagingSourceFactory = { database.getPostsDao().getPosts() },
            remoteMediator = PostRemoteMediator(api = api, dataBase = database,pageNew = pageNew)
        ).flow.map { pagingData ->
            pagingData.map { it.bdUi() }
        }
    }
}
