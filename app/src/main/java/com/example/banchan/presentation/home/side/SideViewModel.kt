package com.example.banchan.presentation.home.side

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.usecase.GetSideDishesUseCase
import com.example.banchan.presentation.home.maincook.Filter
import com.example.banchan.presentation.adapter.home.CommonItemListModel
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SideViewModel @Inject constructor(
    private val getSideDishesUseCase: GetSideDishesUseCase
) : ViewModel() {
    private val filter = MutableStateFlow(Filter.NORMAL)
    val menus = filter.map { filter ->
        val soupDishes = getSideDishesUseCase()
        listOf(CommonItemListModel.Header(soupDishes.size, filter)) + soupDishes.run {
            when (filter) {
                Filter.PRICE_LOW -> sortedWith(compareBy {
                    it.discountPrice.toNum() ?: it.originPrice.toNum()
                })
                Filter.PRICE_HIGH -> sortedWith(compareByDescending {
                    it.discountPrice.toNum() ?: it.originPrice.toNum()
                })
                Filter.SALE -> sortedByDescending { it.discountPercent }
                else -> this
            }
        }.map {
            CommonItemListModel.SmallItem(it)
        }
    }

    fun changeFilter(filter: Filter) {
        viewModelScope.launch {
            this@SideViewModel.filter.emit(filter)
        }
    }
}