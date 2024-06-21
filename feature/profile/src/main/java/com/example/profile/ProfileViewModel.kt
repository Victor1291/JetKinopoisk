package com.example.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.Choice
import com.shu.models.CinemaItem
import com.shu.models.domain.CollectionsRepository
import com.shu.models.profile.ProfileUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: CollectionsRepository
) : ViewModel() {


    val collectionsAll = repository.getCollection()

    private val _interesting = MutableStateFlow<List<CinemaItem>>(emptyList())
    val interesting = _interesting.asStateFlow()

    private val _watched = MutableStateFlow<List<CinemaItem>>(emptyList())
    val watched = _watched.asStateFlow()

    // val watched = getAllCollectionsUseCase.getWatched()
    val ch = Choice()

    val uiProfile =
        combine(watched, collectionsAll, interesting) { watched, collectionsAll, interesting ->
            ProfileUi(
                watched = watched,
                collections = collectionsAll,
                interesting = interesting
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ProfileUi(
                watched = emptyList(),
                collections = emptyList(),
                interesting = emptyList()
            )
        )

    init {
        loadInteresting()
        loadWatched()
    }

    private fun loadInteresting() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getInteresting()
            }.fold(
                onSuccess = { _interesting.value = it },
                onFailure = { Log.d("CountryViewModel", it.message ?: "") }
            )
        }
    }

    fun refresh() {
        loadInteresting()
        loadWatched()
    }

    private fun loadWatched() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getWatched()
            }.fold(
                onSuccess = { _watched.value = it },
                onFailure = { Log.d("CountryViewModel", it.message ?: "") }
            )
        }
    }

    var movieId = 0

    //TODO сделать выбор иконки для коллекции при создании
    fun addCollection(nameCollection: String, icon: Int = 3) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.onAdd(nameCollection, icon)
        }
    }

    fun onUpdateBtn() {}

    fun onDeleteBtn() {
        viewModelScope.launch(Dispatchers.IO) {
            //  repository.clearInteresting()
        }
    }

}