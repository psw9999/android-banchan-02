package com.example.banchan.presentation.home.soup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.home.GetSoupDishesUseCase
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.home.Filter
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoupViewModel @Inject constructor(
    private val getSoupDishesUseCase: GetSoupDishesUseCase,
    getBasketItemUseCase: GetBasketItemUseCase
) : ViewModel() {
    private val refresh = MutableSharedFlow<Boolean>(replay = 1)

    private val _filter = MutableStateFlow(Filter.NORMAL)
    val filter: StateFlow<Filter>
        get() = _filter

    private val _dishes = combine(refresh, _filter) { _, filter ->
        _uiState.update { UiState.Loading }
        val result = getSoupDishesUseCase().getOrNull()
        result?.run {
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
    }

    private val _uiState: MutableStateFlow<UiState<List<ItemModel>>> =
        MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState<List<ItemModel>>>
        get() = _uiState

    init {
        viewModelScope.launch {
            refresh.emit(true)
            combine(_dishes, getBasketItemUseCase()) { dishes, basketList ->
                checkIsCartAdded(dishes, basketList.getOrDefault(listOf()))
            }.collect { dish ->
                _uiState.update {
                    if (dish == null) return@update UiState.Error(Exception(""))
                    if (dish.isEmpty()) UiState.Empty else UiState.Success(dish)
                }
            }
        }
    }

    fun changeFilter(changedFilter: Filter) {
        _filter.value = changedFilter
    }

    fun refresh() {
        viewModelScope.launch {
            refresh.emit(true)
        }
    }

    private fun checkIsCartAdded(
        dishes: List<ItemModel>?,
        basketList: List<BasketItem>
    ): List<ItemModel>? =
        dishes?.map { dish ->
            dish.copy(
                isCartAdded = dish.detailHash in basketList.map { it.hash }
            )
        }

}