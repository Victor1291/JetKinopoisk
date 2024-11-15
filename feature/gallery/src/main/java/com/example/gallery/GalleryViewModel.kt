package com.example.gallery

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.gallery.domain.GalleryRepository
import com.shu.models.gallery_models.GalleryItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface UiState {
    data class Success(
        val pager: Flow<PagingData<GalleryItem>>,
        val total: List<Int>
    ) : UiState

    data class Error(val message: String) : UiState
    data object Loading : UiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel(assistedFactory = GalleryViewModel.Factory::class)
class GalleryViewModel @AssistedInject constructor(
    private val galleryRepository: GalleryRepository,
    @Assisted private val filmId: Int,
) : ViewModel() {

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    var type = "STILL"

    var select = 0
    var indexType = mutableMapOf<String, Int>(
        "STILL" to 0,
        "SCREENSHOT" to 0,
        "SHOOTING" to 0,
        "FAN_ART" to 0,
        "WALLPAPER" to 0,
        "PROMO" to 0,
        "POSTER" to 0,
        "CONCEPT" to 0,
        "COVER" to 0,
    )
    var firstVisibleItemIndex = 0
    // private var filmId = 1

    private var totalList: List<Int> = emptyList()

    /*TODO save scroll position */

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getFirstGallery() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState =
                try {
                    val answer = galleryRepository.getFirstGallery(filmId = filmId, type = type)
                    totalList = answer.second
                    UiState.Success(
                        pager = answer.first,
                        total = totalList
                    )
                } catch (e: IOException) {
                    UiState.Error(message = e.toString())
                } catch (e: HttpException) {
                    UiState.Error(e.toString())
                }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getGallery() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState =
                try {
                    val continuePager = galleryRepository.getGallery(filmId = filmId, type = type)
                    UiState.Success(
                        pager = continuePager,
                        total = totalList
                    )
                } catch (e: IOException) {
                    UiState.Error(message = e.toString())
                } catch (e: HttpException) {
                    UiState.Error(e.toString())
                }
        }
    }

    init {
        Log.d("gallery ViewModel", "init loadFirst Screen")
        getFirstGallery()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun retry() {
        getGallery()
    }

    @AssistedFactory
    internal interface Factory {
        fun create(filmId: Int): GalleryViewModel
    }

}