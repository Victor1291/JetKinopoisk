package com.example.bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shu.models.domain.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {

    fun addCollection(nameCollection: String, icon: Int = 3) {
        //при использовании Retrofit/Room можно не переключать диспатчер
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.onAdd(nameCollection, icon)
        }
    }
}