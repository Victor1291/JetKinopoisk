package com.shu.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.ManyScreens
import com.shu.network.repository.HomeRepository
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
class MainViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    var choiceCity: String = ""

    val currentDay = getTime()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _stateTOpBar = MutableStateFlow<Boolean>(true)
    val stateTOpBar: StateFlow<Boolean> = _stateTOpBar.asStateFlow()

    /* private val _city = MutableStateFlow<List<ILocation>>(emptyList())
     val city = _city.asStateFlow()

     private val _weather = MutableStateFlow<List<IForecastday>>(emptyList())
     val weather = _weather.asStateFlow()*/

    /* private val _searchWidgetState: MutableState<SearchWidgetState> =
         mutableStateOf(value = SearchWidgetState.CLOSED)
     val searchWidgetState: androidx.compose.runtime.State<SearchWidgetState> = _searchWidgetState*/

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: androidx.compose.runtime.State<String> = _searchTextState

    fun getTime(): String {
        val date = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]

        return if (month < 10) "$year-0$month-$day" else "$year-$month-$day"
    }

    /* fun updateSearchWidgetState(newValue: SearchWidgetState) {
         _searchWidgetState.value = newValue
     }*/

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    /* fun setLoadingState() {
         _uiState.value = UiState.Loading
     }*/

    fun changeStateTOpBar(stateNew: Boolean) {
        _stateTOpBar.value = stateNew
    }

    init {
        Log.d("Init Viewmodel", " ******************************** ")
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


    /* fun getAllCity() {
         viewModelScope.launch {
             _city.value = getAllCityUseCase.invoke()
         }
     }

     fun getWeather() {
         viewModelScope.launch {
             _weather.value = getWeatherUseCase.invoke(choiceCity).forecast.forecastday
         }
     }

     private fun saveCity(city: String) {
         choiceCity = city
         viewModelScope.launch {
             dataStoreDataSource.saveCity(city)
         }
     }

     private fun getCityFromDataSource() {
         viewModelScope.launch {
             dataStoreDataSource.city.collect {
                 choiceCity = it
                 updateSearchTextState(it)
                 getWeather(it)
                 getAllCity()
             }
         }
     }*/
}
