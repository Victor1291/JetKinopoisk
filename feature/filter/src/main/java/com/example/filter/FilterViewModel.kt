package com.example.filter

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shu.models.FilmVip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _filter = MutableStateFlow(FilmVip())
    val filter = _filter.asStateFlow()

     fun setFilter(titleNew: FilmVip) {
         _filter.value = titleNew
     }

}