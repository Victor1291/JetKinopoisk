package com.shu.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.ManyScreens
import com.shu.home.domain.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Inject

sealed interface UiState {
    data class Success(val manyScreens: ManyScreens) : UiState
    data object Error : UiState
    data object Loading : UiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private fun getTime(): String {
        val date = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        return if (month < 10) "$year-0$month-$day" else "$year-$month-$day"
    }

    init {
        Log.d("Init HomeViewmodel", " *****  ${getTime()} ***** ")
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            _uiState.value =
                try {
                    UiState.Success(repository.getAllScreen())
                } catch (e: Exception) {
                    Log.e("viewmodelError", "Error $e")
                    UiState.Error
                }
        }
    }
}
