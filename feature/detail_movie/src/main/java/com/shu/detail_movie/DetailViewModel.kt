package com.shu.detail_movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.detail_movie.domain.DetailRepository
import com.shu.models.details.DetailUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiState {
    data class Success(
        val result: DetailUi
    ) : UiState

    data class Error(val message: String) : UiState
    data object Loading : UiState
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var filmId : Int? = null

    init {
        Log.d("Init DetailViewmodel", " *****   ***** ")
    }

    suspend fun getDetailUi(filmId: Int) {
            try {
                _uiState.value = UiState.Success(
                    result = repository.getDetailUi(filmId, "STILL", 1),
                )
            } catch (e: Exception) {
                Log.e("viewmodelError", "Error $e")
                _uiState.value = UiState.Error(e.toString())
            }
    }

    fun toggleFavoriteStatus(favorite: Boolean) = viewModelScope.launch {
        repository.heart(filmId, favorite)
    }

    fun toggleSeeLaterStatus( favorite: Boolean) = viewModelScope.launch {
        repository.seeLater(filmId, favorite)
    }

    fun toggleWatchedStatus(favorite: Boolean) = viewModelScope.launch {
        repository.watched(filmId, favorite)
    }

}
