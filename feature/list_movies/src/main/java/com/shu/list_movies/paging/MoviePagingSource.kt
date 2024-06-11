package com.shu.list_movies.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shu.list_movies.domain.PagingRepository
import com.shu.models.CinemaItem
import com.shu.models.ETitle
import com.shu.models.FilmVip

class MoviePagingSource(
    private val pagingRepository: PagingRepository,
    private val title: FilmVip
) : PagingSource<Int, CinemaItem>() {

    override fun getRefreshKey(state: PagingState<Int, CinemaItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CinemaItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            //Log.d("search source", " idTitle ${title.idTitle} word ${title.keyword}")
            when (title.title) {
                //TODO задать для Premiers год и месяц
                ETitle.Premieres -> pagingRepository.getPremieres(2024, "MAY").items
                ETitle.Popular -> pagingRepository.getPopular(page).items
                ETitle.Top250 -> pagingRepository.getTop250(page).items//лучшее TODO
                ETitle.FilmVip -> pagingRepository.getFilmVip(title).items
                ETitle.SerialVip -> pagingRepository.getSerialVip(page).items
                else -> {
                    Log.d("search source", " word ${title.keyword}")
                    title.page = page
                    pagingRepository.getFilmVip(title).items
                }// вариант с выборками
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