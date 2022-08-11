package com.example.banchan.presentation.home.maincook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.ItemListModel
import com.example.banchan.domain.usecase.GetMainDishesUseCase
import com.example.banchan.presentation.home.maincook.adapter.Type
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCookViewModel @Inject constructor(
    private val getMainDishesUseCase: GetMainDishesUseCase
) : ViewModel() {
    private val type = MutableStateFlow(Type.Grid)
    private val filter = MutableStateFlow(Filter.NORMAL)
    val menus = combine(type, filter) { type, filter ->
        listOf(ItemListModel.Header(type, filter)) + getMainDishesUseCase()
            .run {
                when (filter) {
                    Filter.PRICE_LOW -> this.sortedWith(compareBy {
                        it.discountPrice.toNum() ?: it.originPrice.toNum()
                    })
                    Filter.PRICE_HIGH -> this.sortedWith(compareByDescending {
                        it.discountPrice.toNum() ?: it.originPrice.toNum()
                    })
                    Filter.SALE -> this.sortedByDescending { it.discountPercent }
                    else -> this
                }
            }
            .map {
                if (type == Type.Grid) {
                    ItemListModel.SmallItem(it)
                } else {
                    ItemListModel.LargeItem(it)
                }
            }
    }

    fun changeType(type: Type) {
        viewModelScope.launch {
            this@MainCookViewModel.type.emit(type)
        }
    }

    fun changeFilter(filter: Filter) {
        viewModelScope.launch {
            this@MainCookViewModel.filter.emit(filter)
        }
    }
}

enum class Filter {
    NORMAL, PRICE_HIGH, PRICE_LOW, SALE
}