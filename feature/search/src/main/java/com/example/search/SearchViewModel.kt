package com.example.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.search.domain.PagingSearchRepository
import com.example.search.paging.SearchPagingSource
import com.example.search.paging.SearchPersonPagingSource
import com.shu.models.CinemaItem
import com.shu.models.FilmVip
import com.shu.models.detail_person.SearchPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PagingSearchRepository,
) : ViewModel() {

    private var _title = MutableStateFlow(FilmVip().copy(keyword = ""))
    val title = _title.asStateFlow()
    private var titleIn = title.value

    init {
        title.onEach {
            Log.d("searchViewModel onEach", "2.  $it")
            titleIn = it
        }.launchIn(viewModelScope)
    }

    val pagedMovies: Flow<PagingData<CinemaItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            SearchPagingSource(
                repository,
                titleIn
            )
        }
    ).flow.cachedIn(viewModelScope)

    val pagedPerson: Flow<PagingData<SearchPerson>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            SearchPersonPagingSource(
                repository,
                titleIn.keyword
            )
        }
    ).flow.cachedIn(viewModelScope)

    fun setFilter(titleNew: FilmVip) {
        _title.value = titleNew
    }

}
