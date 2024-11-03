package com.example.bottom_sheet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.collections.Collections
import com.shu.models.domain.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {

    var movieId = 0

    private val collectionsAll = collectionsRepository.getCollection(movieId)

    private val _getCheckedCollection = MutableStateFlow<List<Collections>>(emptyList())
    private val getCheckedCollection = _getCheckedCollection.asStateFlow()

 private var isFirstStart = false

    private var nameCollection = ""

    private fun loadChecked() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                collectionsRepository.getCheckedCollections(movieId)
            }.fold(
                onSuccess = { _getCheckedCollection.value = it },
                onFailure = { Log.d("CountryViewModel", it.message ?: "") }
            )
        }
    }

    private var sizeCollection = 0

    fun refresh() {
        loadChecked()
    }

    val uiCollections =
        combine(
            collectionsAll,
            getCheckedCollection,
        ) { collectionsAll, checked ->
            collectionsAll.map { collection ->
                val check = checked.contains(collection)
                // Log.d("collections", " ${collection.name} is checked = $check ")
                collection.checked = check
            }
            val newSize = collectionsAll.size
            if (isFirstStart) {
                sizeCollection = newSize
            } else {
                if (newSize > sizeCollection) {
                    //Если добавлена новая коллекция. отмечаем и сохраняем в базе
                    addMovieInCollection(collectionsAll.last().collectionId)
                    collectionsAll.last().checked = true
                    sizeCollection = newSize
                }
                //TODO При удалении коллекции.

            }
            collectionsAll
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addOrRemove(collection: Collections) {
        if (collection.checked) {
            addMovieInCollection(collection.collectionId)
        } else {
            removeMovieInCollection(collection.collectionId)
        }
    }

    private fun addMovieInCollection(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.addMovieInDb(collectionId, movieId)
        }
    }

    private fun removeMovieInCollection(collection: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.removeMovieInDb(collection, movieId)
        }
    }

    fun onUpdateBtn() {}

    fun onDeleteBtn() {}

}