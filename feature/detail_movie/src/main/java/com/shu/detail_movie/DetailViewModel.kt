package com.shu.detail_movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.detail_movie.domain.DetailRepository
import com.shu.models.details.DetailUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

sealed interface UiState {
    data class Success(
        val result: DetailUi
    ) : UiState

    data class Error(val message: String) : UiState
    data object Loading : UiState
}

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    private val repository: DetailRepository,
    @Assisted private val filmId: Int,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        Log.d("Init DetailViewmodel", " *****   ***** ")
        viewModelScope.launch {
            getDetailUi()
        }

    }

    private suspend fun getDetailUi() {
        try {
            _uiState.value = UiState.Success(
                result = repository.getDetailUi(filmId, "STILL", 1),
            )
        } catch (e: CancellationException) {
            // Слишком общее исключение здесь отлавливается. Не забывай прокидывать CancellationException
            // Ну и кажется что можно добавить разделение исключений. Например неуспешные статусы показывать одним способом
            //( а они кстати тоже разные могут быть 404/401 и тп) а серверные ошибки IOException другим
            throw e
        } catch (e: Exception) {
            Log.e("viewmodelError", "Error $e")
            _uiState.value = UiState.Error(e.toString())
        }
    }

    fun toggleFavoriteStatus(favorite: Boolean) = viewModelScope.launch {
        repository.heart(filmId, favorite)
    }

    fun toggleSeeLaterStatus(favorite: Boolean) = viewModelScope.launch {
        repository.seeLater(filmId, favorite)
    }

    fun toggleWatchedStatus(favorite: Boolean) = viewModelScope.launch {
        repository.watched(filmId, favorite)
    }

    @AssistedFactory
    internal interface Factory {
        fun create(filmId: Int): DetailViewModel
    }

}
