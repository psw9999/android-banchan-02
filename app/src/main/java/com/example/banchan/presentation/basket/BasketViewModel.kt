package com.example.banchan.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.OrderModel
import com.example.banchan.domain.model.RecentlyProductModel
import com.example.banchan.domain.usecase.basket.*
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import com.example.banchan.domain.usecase.recently.GetRecentProductUseCase
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketItemUseCase: GetBasketItemUseCase,
    private val getRecentProductUseCase: GetRecentProductUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val updateBasketItemUseCase: UpdateBasketItemUseCase,
    private val updateAllBasketIsSelectedUseCase: UpdateAllBasketIsSelectedUseCase,
    private val deleteBasketItemUseCase: DeleteBasketItemUseCase,
    private val deleteSelectedBasketItemUseCase: DeleteSelectedBasketItemUseCase
) : ViewModel() {

    private val basketDbFlow: Flow<List<BasketItem>> =
        getBasketItemUseCase().map { result ->
            result.onSuccess { return@map it }
            listOf()
        }

    private val _isBasketLoading = MutableStateFlow<Boolean>(true)
    private val _isRecentlyLoading = MutableStateFlow<Boolean>(true)

    fun setIsBasketLoading(isLoading: Boolean) {
        _isBasketLoading.value = isLoading
    }

    fun setIsRecentlyLoading(isLoading: Boolean) {
        _isRecentlyLoading.value = isLoading
    }

    val isLoading =
        combine(_isBasketLoading, _isRecentlyLoading) { isBasketLoading, isRecentlyLoading ->
            isBasketLoading || isRecentlyLoading
        }

    private val detailApiMap: MutableMap<String, DetailResponse> = mutableMapOf()

    val basketItemFlow: Flow<List<BasketModel>> =
        basketDbFlow
            .map { basketList ->
                return@map basketList.mapNotNull { basketItem ->
                    checkDetailApiMap(basketItem.hash)
                    return@mapNotNull detailApiMap[basketItem.hash]?.toBasketModel(
                        name = basketItem.name,
                        count = basketItem.count,
                        isSelected = basketItem.isSelected
                    )
                }
            }

    val recentlyProductFlow: Flow<List<RecentlyProductModel>> = getRecentProductUseCase()

    val basketAmountSumFlow: Flow<OrderModel> =
        basketDbFlow
            .map { basketList ->
                var amount = 0
                basketList.forEach { basketItem ->
                    if (basketItem.isSelected) {
                        checkDetailApiMap(basketItem.hash)
                        detailApiMap[basketItem.hash]?.let { detailResponse ->
                            val priceList = detailResponse.data.prices
                            amount += if (priceList.size == 1) (priceList[0].toNum() * basketItem.count)
                            else (priceList[1].toNum() * basketItem.count)
                        }
                    }
                }
                if (amount >= 40000) OrderModel(orderPrice = amount, deliveryFee = 0)
                else OrderModel(orderPrice = amount, deliveryFee = 2500)
            }

    private suspend fun checkDetailApiMap(hash: String) {
        if (!(detailApiMap.containsKey(hash))) {
            getProductDetailUseCase.invoke(hash)
                .onSuccess {
                    detailApiMap[hash] = it
                }
        }
    }

    fun updateBasketItem(basketModel: BasketModel) {
        viewModelScope.launch {
            updateBasketItemUseCase.invoke(
                BasketItem(
                    hash = basketModel.detailHash,
                    name = basketModel.name,
                    count = basketModel.count,
                    isSelected = !basketModel.isChecked,
                )
            )
        }
    }

    fun updateAllBasketIsSelected(isSelected: Int) {
        viewModelScope.launch {
            updateAllBasketIsSelectedUseCase.invoke(isSelected)
        }
    }

    fun deleteSelectedBasketItems() {
        viewModelScope.launch {
            deleteSelectedBasketItemUseCase.invoke()
        }
    }

    fun deleteBasketItem(basketModel: BasketModel) {
        viewModelScope.launch {
            deleteBasketItemUseCase.invoke(
                BasketItem(
                    hash = basketModel.detailHash,
                    name = basketModel.name,
                    count = basketModel.count,
                    isSelected = !basketModel.isChecked,
                )
            )
        }
    }

    fun increaseBasketCount(basketModel: BasketModel) {
        viewModelScope.launch {
            updateBasketItemUseCase.invoke(
                BasketItem(
                    hash = basketModel.detailHash,
                    name = basketModel.name,
                    count = basketModel.count+1,
                    isSelected = basketModel.isChecked,
                )
            )
        }
    }

    fun decreaseBasketCount(basketModel: BasketModel) {
        viewModelScope.launch {
            updateBasketItemUseCase.invoke(
                BasketItem(
                    hash = basketModel.detailHash,
                    name = basketModel.name,
                    count = basketModel.count-1,
                    isSelected = basketModel.isChecked,
                )
            )
        }
    }

}