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
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
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
    private var isSkipRefresh: Boolean = true// savedStateHandle.get<Int>(Constants.PAGE_NEW) ?: 1



    var listPostCashed: Flow<PagingData<Post>>? = null


    init {
        listPostCashed = repository.getPosts(pageNew, isSkipRefresh ).cachedIn(viewModelScope)
        Log.d("viewModelPost", "Posts pageInit = $pageNew ")
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

    fun refresh(){
        isSkipRefresh = false
        listPostCashed = null
        listPostCashed = repository.getPosts(pageNew, isSkipRefresh ).cachedIn(viewModelScope)
    }

}

/*
 item {
                LazyRowPosts(viewModel= viewModel, posts = viewModel.listPostCashed.collectAsLazyPagingItems(), onPostClick = onPostClick, onNextPageClick = {  }) //TODO onNextPageClick
            }
 */
