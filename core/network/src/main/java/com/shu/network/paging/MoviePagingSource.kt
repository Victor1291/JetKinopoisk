package com.shu.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shu.models.CinemaItem
import com.shu.models.ETitle
import com.shu.models.FilmVip
import com.shu.network.ServiceMovieApi
import com.shu.network.models.mapFrom

class MoviePagingSource(
    private val api: ServiceMovieApi,
    private val vip: FilmVip
) : PagingSource<Int, CinemaItem>() {

    override fun getRefreshKey(state: PagingState<Int, CinemaItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CinemaItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            //Log.d("search source", " idTitle ${title.idTitle} word ${title.keyword}")
            when (vip.title) {
                //TODO задать для Premiers год и месяц
                ETitle.Premieres -> api.movies(2024, "MAY").mapFrom().items
                ETitle.Popular -> api.popular(page).mapFrom().items
                ETitle.Top250 -> api.top250(page = page).mapFrom().items//лучшее TODO
                ETitle.SerialVip -> api.serialVip(page = page).mapFrom().items
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
                ).mapFrom().items
            }
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (it.isEmpty()) null else page + 1
                )

            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}

/*
 = "Premiers")
 = "Popular")
Action USA")
Top-250")
= "Dramas Of France")
 = "Serial")
 */