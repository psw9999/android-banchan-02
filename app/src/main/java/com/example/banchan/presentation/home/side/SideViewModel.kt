package com.example.banchan.presentation.home.side

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.R
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.home.GetSideDishesUseCase
import com.example.banchan.presentation.adapter.common.CommonItemListModel
import com.example.banchan.presentation.home.maincook.Filter
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SideViewModel @Inject constructor(
    private val getSideDishesUseCase: GetSideDishesUseCase,
    private val getBasketItemUseCase: GetBasketItemUseCase
) : ViewModel() {
    private var filter = Filter.NORMAL
    private val _dishes =
        MutableStateFlow(
            listOf(
                CommonItemListModel.CommonHeader(titleStrRes = R.string.home_side_title),
                CommonItemListModel.Loading
            )
        )
    val dishes = _dishes.asStateFlow()

    val commonListItems =
        combine(dishes, getBasketItemUseCase()) { dishes, basketList ->
            basketList.onSuccess {
                return@combine checkIsCartAdded(dishes, it)
            }
            dishes
        }

    init {
        viewModelScope.launch {
            _dishes.emit(makeItemModel(filter))
        }
    }

    fun changeFilter(changedFilter: Filter) {
        viewModelScope.launch {
            filter = changedFilter
            _dishes.emit(makeItemModel(filter))
        }
    }

    private suspend fun makeItemModel(filter: Filter): List<CommonItemListModel> {
        val sideDishes = getSideDishesUseCase().getOrNull()
        val preList = mutableListOf(
            CommonItemListModel.CommonHeader(titleStrRes = R.string.home_side_title),
            CommonItemListModel.Filter(sideDishes?.size ?: 0, filter)
        )
        if (sideDishes == null) {
            preList.add(CommonItemListModel.Empty)
            return preList
        }

        return preList + sideDishes.run {
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

    private fun checkIsCartAdded(
        dishes: List<CommonItemListModel>,
        basketList: List<BasketItem>
    ): List<CommonItemListModel> =
        dishes.map { dish ->
            if (dish is CommonItemListModel.SmallItem) {
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