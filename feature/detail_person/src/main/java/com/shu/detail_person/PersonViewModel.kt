package com.shu.detail_person

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.shu.detail_person.domain.PersonRepository
import androidx.lifecycle.ViewModel
import com.shu.models.detail_person.Person
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiState {
    data class Success(
        val person: Person,
    ) : UiState

    data object Error : UiState
    data object Loading : UiState
}


@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) :
    ViewModel() {

    suspend fun getInfo(id: Int): UiState {
        return try {
            UiState.Success(
                person = personRepository.getPerson(id),
            )
        } catch (e: Exception) {
            Log.e("viewmodelError", "Error $e")
            UiState.Error
        }
    }
}