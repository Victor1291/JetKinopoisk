package com.shu.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.database.MovieDatabase
import com.example.database.modelDbo.PostDbo
import com.example.database.modelDbo.RemoteKeysPost
import com.shu.network.ServiceMovieApi
import com.shu.network.models.media_posts.apiBd
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val api: ServiceMovieApi,
    private val dataBase: MovieDatabase,
) : RemoteMediator<Int, PostDbo>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (dataBase.getRemoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostDbo>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = api.getPosts(page)

            val posts = apiResponse.items
            val endOfPaginationReached = posts.isEmpty()

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.getRemoteKeysDao().clearRemoteKeys()
                    dataBase.getMovieMediatorDao().clearAllGames()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = posts.map { post ->
                    RemoteKeysPost(
                        kinopoiskId = post.kinopoiskId ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey,
                    )
                }

                dataBase.getRemoteKeysPostDao().insertAll(remoteKeys)
                dataBase.getPostsDao().insertAll(posts.map { it.apiBd(page) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PostDbo>): RemoteKeysPost? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.kinopoiskId?.let { id ->
                dataBase.getRemoteKeysPostDao().getRemoteKeyByPostID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PostDbo>): RemoteKeysPost? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { post ->
            dataBase.getRemoteKeysPostDao().getRemoteKeyByPostID(post.kinopoiskId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PostDbo>): RemoteKeysPost? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { post ->
            dataBase.getRemoteKeysPostDao().getRemoteKeyByPostID(post.kinopoiskId)
        }
    }

}