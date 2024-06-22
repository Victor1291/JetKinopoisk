package com.example.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gallery.domain.GalleryRepository
import com.example.gallery.paging.GalleryPagingSource
import com.shu.models.gallery_models.GalleryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
) : ViewModel() {

    private var title = "STILL"
    private var filmId = 1

    val pagedMovies: Flow<PagingData<GalleryItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { GalleryPagingSource(galleryRepository, filmId, title) }
    ).flow.cachedIn(viewModelScope)


    init {
        Log.d("gallery ViewModel", "init loadFirst Screen")
    }

    fun setId(id: Int) {
        this.filmId = id
    }

    fun setTitle(titleNew: String) {
        title = titleNew
    }
}