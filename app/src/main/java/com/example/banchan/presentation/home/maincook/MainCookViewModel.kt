package com.example.banchan.presentation.home.maincook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.repository.HistoryRepository
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.domain.usecase.GetMainDishesUseCase
import com.example.banchan.presentation.adapter.main.MainItemListModel
import com.example.banchan.presentation.adapter.main.Type
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCookViewModel @Inject constructor(
    private val getMainDishesUseCase: GetMainDishesUseCase
) : ViewModel() {
    var type = Type.Grid
        private set
    private var filter = Filter.NORMAL
    private val _dishes =
        MutableStateFlow(listOf(MainItemListModel.MainHeader(), MainItemListModel.Loading))
    val dishes = _dishes.asStateFlow()

    init {
        viewModelScope.launch {
            _dishes.emit(makeItemModel(type, filter))
        }
    }

    fun changeType(changedType: Type) {
        viewModelScope.launch {
            type = changedType
            _dishes.emit(makeItemModel(type, filter))
        }
    }

    fun changeFilter(changedFilter: Filter) {
        viewModelScope.launch {
            filter = changedFilter
            _dishes.emit(makeItemModel(type, filter))
        }
    }

    private suspend fun makeItemModel(type: Type, filter: Filter): List<MainItemListModel> {
        val result = getMainDishesUseCase()
        val preList = mutableListOf(
            MainItemListModel.MainHeader(),
            MainItemListModel.Filter(type, filter)
        )
        if (result.isEmpty()) preList.add(MainItemListModel.Empty)

        return preList + result.run {
            when (filter) {
                Filter.PRICE_LOW -> sortedWith(compareBy {
                    it.discountPrice.toNum() ?: it.originPrice.toNum()
                })
                Filter.PRICE_HIGH -> sortedWith(compareByDescending {
                    it.discountPrice.toNum() ?: it.originPrice.toNum()
                })
                Filter.SALE -> this.sortedByDescending { it.discountPercent }
                else -> this
            }
        }
            .map {
                if (type == Type.Grid) {
                    MainItemListModel.SmallItem(it)
                } else {
                    MainItemListModel.LargeItem(it)
                }
            }
    }
}

enum class Filter {
    NORMAL, PRICE_HIGH, PRICE_LOW, SALE
}