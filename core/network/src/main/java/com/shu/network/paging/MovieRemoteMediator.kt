package com.shu.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.database.MovieDatabase
import com.example.database.RemoteKeysDao
import com.example.database.modelDbo.MovieMediaDbo
import com.example.database.modelDbo.RemoteKeys
import com.shu.models.ETitle
import com.shu.models.FilmVip
import com.shu.network.ServiceMovieApi
import com.shu.network.models.mapFrom
import com.shu.network.models.mapFromApiToBd
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: ServiceMovieApi,
    private val dataBase: MovieDatabase,
    private val vip: FilmVip,
    private val isSkipRefresh: Boolean = true
) : RemoteMediator<Int, MovieMediaDbo>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (dataBase.getRemoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout && isSkipRefresh
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieMediaDbo>
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
            val apiResponse = when (vip.title) {
                //TODO задать для Premiers год и месяц
                ETitle.Premieres -> api.movies(2024, "MAY").mapFrom()
                ETitle.Popular -> api.popular(page).mapFrom()
                ETitle.Top250 -> api.top250(page = page).mapFrom()//лучшее TODO
                ETitle.SerialVip -> api.serialVip(page = page).mapFrom()
                else -> api.filmVip(
                    page = vip.page,
                    country = vip.country,
                    genres = vip.genres,
                    order = vip.order,
                    type = vip.type,
                    ratingFrom = vip.ratingFrom,
                    ratingTo = vip.ratingTo,
                    yearFrom = vip.yearFrom,
                    yearTo = vip.yearTo,
                    keyword = vip.keyword
                ).mapFrom()
            }

            val movies = apiResponse.items
            val endOfPaginationReached = movies.isEmpty()

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.getRemoteKeysDao().clearRemoteKeys()
                    dataBase.getMovieMediatorDao().clearAllGames()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map { movie ->
                    RemoteKeys(
                        kinopoiskId = movie.kinopoiskId ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                dataBase.getRemoteKeysDao().insertAll(remoteKeys)
                dataBase.getMovieMediatorDao().insertAll(movies.map { it.mapFromApiToBd(page) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieMediaDbo>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.kinopoiskId?.let { id ->
                dataBase.getRemoteKeysDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieMediaDbo>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { game ->
            dataBase.getRemoteKeysDao().getRemoteKeyByMovieID(game.kinopoiskId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieMediaDbo>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { game ->
            dataBase.getRemoteKeysDao().getRemoteKeyByMovieID(game.kinopoiskId)
        }
    }

}