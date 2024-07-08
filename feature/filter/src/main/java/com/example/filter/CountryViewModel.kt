package com.shu.cinemaworld.ui.setingssearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.Countries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
   // private val movieUseCase: PagedMoviesUseCase
) : ViewModel() {

    val wordSearch = MutableStateFlow("")

    private val _country = MutableStateFlow<List<Countries>>(emptyList())
    val country = combine(_country, wordSearch) { countries, wordSearch ->
        if (wordSearch.isNotEmpty())
            countries.filter { country ->
                country.country?.contains(wordSearch,true) ?: false
            }
        else countries
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _country.value
    )

    init {
        loadCountry()
    }

    private fun loadCountry() {
        /* viewModelScope.launch(Dispatchers.IO) {
             kotlin.runCatching {
                 movieUseCase.executeCountries()
             }.fold(
                 onSuccess = { _country.value = it },
                 onFailure = { Log.d("CountryViewModel", it.message ?: "") }
             )
         }*/
    }
    fun refresh() {
        loadCountry()
    }
}
