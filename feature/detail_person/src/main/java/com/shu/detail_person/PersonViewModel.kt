package com.shu.detail_person

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shu.detail_person.domain.PersonRepository
import com.shu.models.detail_person.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface UiState {
    data class Success(
        val person: Person,
    ) : UiState

    data class Error(val message: String) : UiState
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
            UiState.Error(e.toString())
        }
    }
}