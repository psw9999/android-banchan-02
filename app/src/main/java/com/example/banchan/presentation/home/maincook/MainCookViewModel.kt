package com.example.banchan.presentation.home.maincook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.ItemListModel
import com.example.banchan.domain.usecase.GetMainDishesUseCase
import com.example.banchan.presentation.home.maincook.adapter.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCookViewModel @Inject constructor(
    private val getMainDishesUseCase: GetMainDishesUseCase
) : ViewModel() {
    private val filter = MutableStateFlow(Type.Grid)
    val fake = filter.map { filter ->
        listOf(ItemListModel.Header(filter)) + getMainDishesUseCase().map {
            if (filter == Type.Grid) {
                ItemListModel.SmallItem(it)
            } else {
                ItemListModel.LargeItem(it)
            }
        }
    }

    fun changeType(type: Type) {
        viewModelScope.launch {
            filter.emit(type)
        }
    }
}