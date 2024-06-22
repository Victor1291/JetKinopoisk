package com.shu.detail_movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.detail_movie.domain.DetailRepository
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.details.DetailUi
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.profile.ProfileUi
import com.shu.models.similar_models.ListSimilar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    private val _film = MutableStateFlow<DetailMovie>(DetailMovie())
    private val film: StateFlow<DetailMovie> = _film.asStateFlow()

    private val _getActorFilm = MutableStateFlow<List<Actor>>(emptyList())
    private val actorFilm: StateFlow<List<Actor>> = _getActorFilm.asStateFlow()

    private val _getGallery = MutableStateFlow<ListGalleryItems>( ListGalleryItems())
    private val gallery: StateFlow<ListGalleryItems> = _getGallery.asStateFlow()

    private val _getSimilarsFilm = MutableStateFlow<ListSimilar>(ListSimilar())
    private val similarsFilm: StateFlow<ListSimilar> = _getSimilarsFilm.asStateFlow()

    val uiState =
        combine(film, actorFilm, gallery,similarsFilm) {film, actorFilm, gallery,similarsFilm ->
            DetailUi(
                film = film,
                actorFilm = actorFilm,
                gallery = gallery ,
                similarsFilm = similarsFilm
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DetailUi(
                film = DetailMovie(),
                actorFilm = emptyList(),
                gallery = ListGalleryItems() ,
                similarsFilm = ListSimilar()
            )
        )
    init {
        Log.d("Init DetailViewmodel", " *****   ***** ")
    }

   suspend fun getFilm(id: Int) {
            kotlin.runCatching {
                repository.getFilm(id)
            }.fold(
                onSuccess = { _film.value = it },
                onFailure = {
                    Log.d("getFilm", it.message ?: "")
                }
            )
    }
    suspend fun getActorFilm(id: Int) {
        kotlin.runCatching {
            repository.getActorFilm(id)
        }.fold(
            onSuccess = { _getActorFilm.value = it },
            onFailure = { Log.d("getActorFilm", it.message ?: "") }
        )
    }

    suspend fun getGallery(id: Int) {
        kotlin.runCatching {
            repository.getGallery(id, "STILL", 1)
        }.fold(
            onSuccess = { _getGallery.value = it },
            onFailure = { Log.d("getGallery", it.message ?: "") }
        )
    }
    suspend fun getSimilarsFilm(id: Int) {
        kotlin.runCatching {
            repository.getSimilarsFilm(id)
        }.fold(
            onSuccess = { _getSimilarsFilm.value = it },
            onFailure = { Log.d("getSimilarsFilm", it.message ?: "") }
        )
    }
}
