package com.example.gallery.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gallery.domain.GalleryRepository
import com.shu.models.gallery_models.GalleryItem

class GalleryPagingSource(
    private val repository: GalleryRepository,
    private val filmId: Int,
    private val type: String
) : PagingSource<Int, GalleryItem>() {

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getGallery(filmId, page, type).items
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                return@fold LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}
