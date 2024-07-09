package com.shu.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shu.models.gallery_models.GalleryItem
import com.shu.network.ServiceMovieApi
import com.shu.network.models.gallery_models.toListGalleryItems

class GalleryPagingSource(
    private val api: ServiceMovieApi,
    private val filmId: Int,
    private val type: String
) : PagingSource<Int, GalleryItem>() {

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            api.galleryTotal(filmId, page, type).toListGalleryItems().items
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey =  if (page == FIRST_PAGE) null else page - 1,
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
