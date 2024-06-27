package com.example.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.search.domain.PagingSearchRepository
import com.shu.models.detail_person.SearchPerson

class SearchPersonPagingSource(
    private val pagingRepository: PagingSearchRepository,
    private val name: String,
) : PagingSource<Int, SearchPerson>() {

    override fun getRefreshKey(state: PagingState<Int, SearchPerson>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchPerson> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            pagingRepository.searchPerson(name, page).items
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