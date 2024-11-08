package com.shu.posts

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shu.models.Constants
import com.shu.models.media_posts.Post
import com.shu.posts.domain.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var pageNew = 4// savedStateHandle.get<Int>(Constants.PAGE_NEW) ?: 1

    val listPostCashed: Flow<PagingData<Post>> =
        repository.getPosts(pageNew).cachedIn(viewModelScope)

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
        Log.d("viewModelHome", " pageInit = $pageNew ")
        Log.d("viewModelHome", " *****  ${getTime()} ***** ")
    }


    //сохраняем если изменился номер.
    fun setPage(page: Int) {

        if (page != pageNew) {
            Log.d("viewModelHome", " page = $page ")
            pageNew = page
            savedStateHandle[Constants.PAGE_NEW] = page
            val pageCheck = savedStateHandle.get<Int>(Constants.PAGE_NEW) ?: 1
            Log.d("viewModelHome", " pageCheck = $pageCheck ")
        }
    }

}

/*
 item {
                LazyRowPosts(viewModel= viewModel, posts = viewModel.listPostCashed.collectAsLazyPagingItems(), onPostClick = onPostClick, onNextPageClick = {  }) //TODO onNextPageClick
            }
 */
