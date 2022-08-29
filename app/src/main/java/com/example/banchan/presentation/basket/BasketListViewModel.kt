package com.example.banchan.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.OrderModel
import com.example.banchan.domain.usecase.basket.*
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import com.example.banchan.domain.usecase.history.InsertHistoryItemsUseCase
import com.example.banchan.presentation.UiState
import com.example.banchan.util.DEFAULT_DELIVERY_FEE
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class BasketListViewModel @Inject constructor(
    getBasketItemUseCase: GetBasketItemUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val updateBasketItemUseCase: UpdateBasketItemUseCase,
    private val updateAllBasketIsSelectedUseCase: UpdateAllBasketIsSelectedUseCase,
    private val deleteBasketItemUseCase: DeleteBasketItemUseCase,
    private val deleteSelectedBasketItemUseCase: DeleteSelectedBasketItemUseCase,
    private val insertHistoryItemsUseCase: InsertHistoryItemsUseCase
) : ViewModel() {

    private val basketList: Flow<List<BasketItem>?> =
        getBasketItemUseCase().map { result ->
            if (result.isSuccess) return@map result.getOrNull()
            else return@map null
        }

    private val basketRefresh = MutableSharedFlow<Boolean>(replay = 1)

    val successHistoryId = MutableSharedFlow<Long>()
    private val detailApiMap: MutableMap<String, DetailResponse> = mutableMapOf()

    private val _basketUiState: MutableStateFlow<UiState<List<BasketModel>>> = MutableStateFlow(UiState.Init)
    val basketUiState = _basketUiState.asStateFlow()

    init {
        viewModelScope.launch {
            basketRefresh.emit(true)
            combine(basketList, basketRefresh) { basketList, _ ->
                basketList
            }.map { basketList ->
                if (basketList == null) return@map (UiState.Error(Exception("DB Error")))
                else {
                    if (basketList.isEmpty()) return@map (UiState.Empty)
                    else {
                        try {
                            val result = basketList.asFlow()
                                .flatMapMerge { basketItem ->
                                    flow {
                                        checkDetailApiMap(basketItem.hash)
                                        emit(
                                            detailApiMap[basketItem.hash]!!.toBasketModel(
                                                name = basketItem.name,
                                                count = basketItem.count,
                                                isSelected = basketItem.isSelected,
                                                time = basketItem.time
                                            )
                                        )
                                    }
                                }.toList().sortedBy { it.time }
                            return@map UiState.Success(result)
                        } catch(e: Exception) {
                            return@map UiState.Error(Exception("Network Error"))
                        }
                    }
                }
            }.collectLatest { _basketUiState.emit(it) }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            basketRefresh.emit(true)
        }
    }

    val isAllBasketItemSelectedFlow: Flow<Boolean> =
        basketList
            .map { basketList ->
                return@map basketList?.all { it.isSelected } ?: false
            }

    val basketAmountSum: StateFlow<OrderModel> = combine(
        basketList, basketRefresh
    ) { basketList, _ ->
        var amount = 0
        try {
            basketList
                ?.map { basketItem ->
                    if (basketItem.isSelected) {
                        checkDetailApiMap(basketItem.hash)
                        detailApiMap[basketItem.hash]!!.let { detailResponse ->
                            val priceList = detailResponse.data.prices
                            amount += if (priceList.size == 1) (priceList[0].toNum() * basketItem.count)
                            else (priceList[1].toNum() * basketItem.count)
                        }
                    }
                }
            if (amount >= 40000) OrderModel(orderPrice = amount, deliveryFee = 0)
            else OrderModel(orderPrice = amount, deliveryFee = DEFAULT_DELIVERY_FEE)
        } catch (e: Exception) {
            return@combine OrderModel(orderPrice = 0, deliveryFee = DEFAULT_DELIVERY_FEE)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderModel(orderPrice = 0, deliveryFee = DEFAULT_DELIVERY_FEE)
    )

    private suspend fun checkDetailApiMap(hash: String) {
        if (!(detailApiMap.containsKey(hash))) {
            getProductDetailUseCase.invoke(hash)
                .onSuccess {
                    detailApiMap[hash] = it
                }
                .onFailure {
                    throw it
                }
        }
    }

    fun checkBasketItem(basketModel: BasketModel) {
        viewModelScope.launch {
            updateBasketItemUseCase.invoke(
                BasketItem(
                    hash = basketModel.detailHash,
                    name = basketModel.name,
                    count = basketModel.count,
                    isSelected = !basketModel.isChecked,
                    time = basketModel.time
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
                    isSelected = basketModel.isChecked,
                    time = basketModel.time
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
                    count = basketModel.count + 1,
                    isSelected = basketModel.isChecked,
                    time = basketModel.time
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
                    count = basketModel.count - 1,
                    isSelected = basketModel.isChecked,
                    time = basketModel.time
                )
            )
        }
    }

    fun insertHistoryItemList(deliveryFee: Int) {
        viewModelScope.launch {
            val historyList = getHistoryItemList()
            val result = insertHistoryItemsUseCase(historyList, deliveryFee)
            result.onSuccess {
                deleteSelectedBasketItemUseCase()
                successHistoryId.emit(it)
            }
        }
    }

    private suspend fun getHistoryItemList(): List<HistoryItem> {
        val historyList = mutableListOf<HistoryItem>()
        val basketList = basketUiState.first()
        if (basketList is UiState.Success) {
            basketList.item.forEach {
                if (it.isChecked) {
                    historyList.add(
                        HistoryItem(
                            imageUrl = it.image,
                            count = it.count,
                            originPrice = it.price,
                            name = it.name
                        )
                    )
                }
            }
        }
        return historyList
    }

}