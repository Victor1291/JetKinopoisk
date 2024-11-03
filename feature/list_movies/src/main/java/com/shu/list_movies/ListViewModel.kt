package com.shu.list_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shu.list_movies.domain.PagingRepository
import com.shu.list_movies.paging.MoviePagingSource
import com.shu.models.CinemaItem
import com.shu.models.FilmVip
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

@HiltViewModel(assistedFactory = ListViewModel.Factory::class)
class ListViewModel @AssistedInject constructor(
    private val repository: PagingRepository,
    @Assisted private val film: FilmVip,
) : ViewModel() {

    //TODO подключить Mediator

    val pagedMovies: Flow<PagingData<CinemaItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            MoviePagingSource(
                repository,
                film
            )
        }
    ).flow.cachedIn(viewModelScope)

    @AssistedFactory
    internal interface Factory {
        fun create(film: FilmVip): ListViewModel
    }
}
