package com.example.banchan.presentation.home.maincook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.response.ItemListModel
import com.example.banchan.fakeData
import com.example.banchan.presentation.home.maincook.adapter.Filter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainCookViewModel : ViewModel() {
    val filter = MutableStateFlow(Filter.Grid)
    val fake = filter.map { filter ->
        listOf(ItemListModel.Header) + fakeData.map {
            if (filter == Filter.Grid) {
                ItemListModel.SmallItem(it)
            } else {
                ItemListModel.LargeItem(it)
            }
        }
    }

    fun changeMode(type: Filter) {
        viewModelScope.launch {
            filter.emit(type)
        }
    }
}