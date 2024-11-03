package com.shu.detail_person

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.detail_person.domain.PersonRepository
import com.shu.models.detail_person.Person
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface UiState {
    data class Success(
        val person: Person,
    ) : UiState

    data class Error(val message: String) : UiState
    data object Loading : UiState
}


@HiltViewModel(assistedFactory = PersonViewModel.Factory::class)
class PersonViewModel @AssistedInject constructor(
    private val personRepository: PersonRepository,
    @Assisted private val personId: Int
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getInfo()
        }
    }
    private suspend fun getInfo() {
        try {
            _uiState.value = UiState.Success(
                person = personRepository.getPerson(personId),
            )
        } catch (e: Exception) {
            Log.e("viewmodelError", "Error $e")
            UiState.Error(e.toString())
        }
    }

    @AssistedFactory
    internal interface Factory {
        fun create(personId: Int): PersonViewModel
    }
}