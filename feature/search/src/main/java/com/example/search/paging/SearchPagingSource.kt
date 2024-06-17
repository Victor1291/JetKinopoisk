package com.example.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.search.domain.PagingSearchRepository
import com.shu.models.CinemaItem
import com.shu.models.FilmVip

class SearchPagingSource(
    private val pagingRepository: PagingSearchRepository,
    private val title: FilmVip
) : PagingSource<Int, CinemaItem>() {

    override fun getRefreshKey(state: PagingState<Int, CinemaItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CinemaItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            title.page = page
            pagingRepository.getFilmVip(title).items
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