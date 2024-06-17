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
        val actors: List<Actor>,
        val gallery: ListGalleryItems,
        val similar: ListSimilar
    ) : UiState

    data object Error : UiState
    data object Loading : UiState
}


@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) :
    ViewModel() {

    private val _person = MutableStateFlow<List<ListItem>>(getItems())
    val person: StateFlow<List<ListItem>> = _person.asStateFlow()

    fun loadDetails(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                //_isLoading.value = true
                personRepository.execute(kinopoiskId)
            }.fold(
                onSuccess = {
                    Log.d("dataPerson", "${it.size} Пришло")
                    _person.value = it
                },
                onFailure = {
                    Log.e("ActorViewModel", "Error  ${it.message}")
                }
            )
            //_isLoading.value = false
        }
    }
}