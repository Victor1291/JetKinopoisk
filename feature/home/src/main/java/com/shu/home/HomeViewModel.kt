package com.shu.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shu.home.domain.HomeRepository
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Inject

sealed interface UiState {
    data class Success(
        val manyScreens: ManyScreens,
        val posts: ListPosts
    ) : UiState

    data object Error : UiState
    data object Loading : UiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

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
    }

    suspend fun getInfo(): UiState {
        return try {
            UiState.Success(
                manyScreens = repository.getAllScreen(),
                posts = repository.getPosts(1)
            )
        } catch (e: Exception) {
            Log.e("viewmodelError", "Error $e")
            UiState.Error
        }
    }
}
