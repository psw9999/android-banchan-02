package com.example.banchan.presentation.home.soup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.usecase.GetSoupDishesUseCase
import com.example.banchan.presentation.home.maincook.Filter
import com.example.banchan.presentation.home.soup.adapter.SoupItemListModel
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoupViewModel @Inject constructor(
    private val getSoupDishesUseCase: GetSoupDishesUseCase
) : ViewModel() {
    private val filter = MutableStateFlow(Filter.NORMAL)
    val menus = filter.map { filter ->
        val soupDishes = getSoupDishesUseCase()
        listOf(SoupItemListModel.Header(soupDishes.size, filter)) + soupDishes.run {
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
            SoupItemListModel.SmallItem(it)
        }
    }

    fun changeFilter(filter: Filter) {
        viewModelScope.launch {
            this@SoupViewModel.filter.emit(filter)
        }
    }
}