package com.example.banchan.presentation.home.maindish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.home.GetMainDishesUseCase
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.main.MainItemListModel
import com.example.banchan.presentation.adapter.main.Type
import com.example.banchan.presentation.home.Filter
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainDishViewModel @Inject constructor(
    private val getMainDishesUseCase: GetMainDishesUseCase,
    getBasketItemUseCase: GetBasketItemUseCase
) : ViewModel() {
    private var changeTypeJob: Job? = null
    private val refresh = MutableSharedFlow<Boolean>(replay = 1)

    private val _type = MutableStateFlow(Type.Grid)
    val type: StateFlow<Type>
        get() = _type

    private val _filter = MutableStateFlow(Filter.NORMAL)
    val filter: StateFlow<Filter>
        get() = _filter

    private val _uiState: MutableStateFlow<UiState<List<MainItemListModel>>> =
        MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState<List<MainItemListModel>>>
        get() = _uiState

    private val _dishes = combine(refresh, _type, _filter) { _, type, filter ->
        _uiState.update { UiState.Loading }
        val result = getMainDishesUseCase().getOrNull()
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
        }?.map {
            if (type == Type.Grid) {
                MainItemListModel.MediumItem(it)
            } else {
                MainItemListModel.LargeItem(it)
            }
        }
    }

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

    fun changeType(changedType: Type) {
        changeTypeJob?.cancel()

        changeTypeJob = viewModelScope.launch {
            delay(100L)
            _type.value = changedType
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
        dishes: List<MainItemListModel>?,
        basketList: List<BasketItem>
    ): List<MainItemListModel>? =
        dishes?.map { dish ->
            if (dish is MainItemListModel.MediumItem) {
                dish.copy(
                    item = dish.item.copy(
                        isCartAdded = dish.item.detailHash in basketList.map { it.hash }
                    )
                )
            } else if (dish is MainItemListModel.LargeItem) {
                dish.copy(
                    item = dish.item.copy(
                        isCartAdded = dish.item.detailHash in basketList.map { it.hash }
                    )
                )
            } else {
                dish
            }
        }
}