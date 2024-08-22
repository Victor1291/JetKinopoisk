package com.example.filter

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filter.domain.FilterRepository
import com.shu.models.Countries
import com.shu.models.FilmVip
import com.shu.models.Genres
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
class FilterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: FilterRepository
) : ViewModel() {


    private var _filter = MutableStateFlow(FilmVip())
    val filter = _filter.asStateFlow()

     fun setFilter(titleNew: FilmVip) {
         _filter.value = titleNew
     }

    private val _searchTextState= MutableStateFlow(value = "")
    val searchTextState = _searchTextState.asStateFlow()

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    private val _country = MutableStateFlow<List<Countries>>(emptyList())
    val country = combine(_country, searchTextState) { countries, wordSearch ->
        if (wordSearch.isNotEmpty())
            countries.filter { country ->
                country.country.contains(wordSearch,true)
            }
        else countries
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _country.value
    )

    private val _genres = MutableStateFlow<List<Genres>>(emptyList())
    val genres = combine(_genres, searchTextState) { genries, wordSearch ->
        if (wordSearch.isNotEmpty())
            genries.filter { country ->
                country.genre.contains(wordSearch,true)
            }
        else genries
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _genres.value
    )

    private fun loadCountry() {
         viewModelScope.launch(Dispatchers.IO) {
             kotlin.runCatching {
                 repository.getFilters()
             }.fold(
                 onSuccess = { _country.value = it.countries },
                 onFailure = { Log.d("CountryViewModel", it.message ?: "") }
             )
         }
    }
    fun refreshCountry() {
        loadCountry()
    }

        private fun loadGenres() {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    repository.getFilters()
                }.fold(
                    onSuccess = { _genres.value = it.genres },
                    onFailure = { Log.d("CountryViewModel", it.message ?: "") }
                )
            }
        }

    fun refreshGenres() {
        loadGenres()
    }

}