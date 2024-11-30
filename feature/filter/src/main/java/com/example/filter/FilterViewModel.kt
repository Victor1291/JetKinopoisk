package com.example.filter

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filter.domain.FilterRepository
import com.shu.models.FilmVip
import com.shu.models.ListFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: FilterRepository
) : ViewModel() {


    private var _filter = MutableStateFlow(FilmVip())
    val filter = _filter.asStateFlow()

    fun setFilter(titleNew: FilmVip) {
        _filter.value = titleNew
    }

    private var _filterCG = MutableStateFlow<ListFilters>(ListFilters(genres = emptyList(), countries = emptyList()))
    val filterCG = _filterCG.asStateFlow()


    init {
        loadCountryAndGenres()
    }

    private fun loadCountryAndGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getFilters()
            }.fold(
                onSuccess = { _filterCG.value = it },
                onFailure = { Log.d("CountryViewModel", it.message ?: "") }
            )
        }
    }

    fun refreshCountry() {
        loadCountryAndGenres()
    }


}