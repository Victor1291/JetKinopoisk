package com.shu.jetcinema

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _stateTOpBar = MutableStateFlow<Boolean>(true)
    val stateTOpBar: StateFlow<Boolean> = _stateTOpBar.asStateFlow()

    fun getTime(): String {
        val date = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]

        return if (month < 10) "$year-0$month-$day" else "$year-$month-$day"
    }

    fun changeStateTOpBar(stateNew: Boolean) {
        _stateTOpBar.value = stateNew
    }

    init {
        Log.d("Init MainViewmodel", " *****  ${getTime()} ***** ")
    }
}
