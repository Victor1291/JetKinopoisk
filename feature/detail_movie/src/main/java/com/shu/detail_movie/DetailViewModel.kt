package com.shu.detail_movie

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shu.detail_movie.domain.DetailRepository
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface UiState {
    data class Success(
        val movie: DetailMovie,
        val actors: List<Actor>,
        val gallery: ListGalleryItems,
        val similar: ListSimilar
    ) : UiState

    data object Error : UiState
    data object Loading : UiState
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

  /*  private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()*/

    init {
        Log.d("Init DetailViewmodel", " *****   ***** ")
    }

    suspend fun getFilm(id: Int): UiState {
        return try {
            UiState.Success(
                movie = repository.getFilm(id),
                actors = repository.getActorFilm(id),
                gallery = repository.getGallery(id, "STILL", 1),
                similar = repository.getSimilarsFilm(id)
            )
        } catch (e: Exception) {
            Log.e("viewmodelError", "Error $e")
            UiState.Error
        }
    }


}
