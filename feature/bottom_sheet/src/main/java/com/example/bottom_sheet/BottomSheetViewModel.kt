package com.example.bottom_sheet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.domain.CollectionsRepository
import com.shu.models.collections.Collections
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

    var favorite = false
    var select = false

    val isNewCollection = MutableStateFlow(false)
    var nameCollection = ""

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

    fun refresh() {
        loadChecked()
    }

    val uiCollections =
        combine(
            collectionsAll,
            getCheckedCollection,
            isNewCollection
        ) { collectionsAll, checked, isNewCollection ->
            collectionsAll.map { collection ->
                val check = checked.contains(collection)
               // Log.d("collections", " ${collection.name} is checked = $check ")
                collection.checked = check
            }
            if (isNewCollection && collectionsAll.last().name == nameCollection) {
                //Если добавлена новая коллекция. отмечаем и сохраняем в базе
                Log.d("collection save", "name ${collectionsAll.last().name} ")
                addMovieInCollection(collectionsAll.last().collectionId)
                collectionsAll.last().checked = true
            }
            collectionsAll
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun addCollection(nameCollection: String, icon: Int = 3) {
        //TODO
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.onAdd(nameCollection, icon)
        }

    }

    fun addMovieInCollection(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.addMovieInDb(collectionId, movieId)
        }
    }

    fun removeMovieInCollection(collection: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.removeMovieInDb(collection, movieId)
        }
    }

    fun setNewCollection() {
        isNewCollection.value = true
    }

    fun onUpdateBtn() {}

    fun onDeleteBtn() {}

}