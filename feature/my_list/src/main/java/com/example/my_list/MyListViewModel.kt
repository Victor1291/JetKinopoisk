package com.example.my_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.CinemaItem
import com.shu.models.domain.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val repository: CollectionsRepository
) : ViewModel() {

    private val _listMovie = MutableStateFlow<List<CinemaItem>>(emptyList())
    val listMovie = _listMovie.asStateFlow()

    private fun loadCollection(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                when (name) {

                    "Просмотренные" -> {
                        repository.getWatched()
                    }
                    "Вам было интересно" -> {
                        repository.getInteresting()
                    }
                    else -> {
                        repository.getListMovie(name.toInt())
                    }
                }
            }.fold(
                onSuccess = { _listMovie.value = it },
                onFailure = { Log.d("CountryViewModel", it.message ?: "") }
            )
        }
    }

    fun refresh(name: String) {
        loadCollection(name)
    }

    fun clearCollection(name: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (name) {

                "Просмотренные" -> {
                    repository.clearWatched()
                }
                "Вам было интересно" -> {
                    repository.clearInteresting()
                }
                else -> {
                    repository.clearCollection(name.toInt())
                }
            }
        }
    }
}