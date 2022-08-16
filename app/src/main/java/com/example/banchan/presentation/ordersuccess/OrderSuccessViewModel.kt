package com.example.banchan.presentation.ordersuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.domain.usecase.GetHistoryByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OrderSuccessViewModel @Inject constructor(
    private val getHistoryByIdUseCase: GetHistoryByIdUseCase
) : ViewModel() {
    val history = getHistoryByIdUseCase(1)
    val headerUiState = history.map { result ->
        result.getOrNull()?.let {
            OrderCommonListModel.Header(
                time = it.history.remainTime,
                count = it.items.size
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderCommonListModel.Header(0, 0)
    )
    val itemsUiState = history.map { result ->
        result.getOrNull()?.let {
            it.items.map { item -> OrderSuccessListModel.Item(item) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )
    val footerUiState = history.map { result ->
        result.getOrNull()?.let {
            val defaultDeliveryFee = 2500
            val orderPrice = it.items.sumOf { item -> item.originPrice * item.count }
            OrderCommonListModel.Footer(
                orderPrice = orderPrice,
                deliveryFee = defaultDeliveryFee,
                totalPrice = orderPrice + defaultDeliveryFee
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderCommonListModel.Footer(0, 0, 0)
    )
}

sealed interface OrderCommonListModel {
    data class Header(val time: Int, val count: Int) : OrderCommonListModel
    data class Footer(val orderPrice: Int, val deliveryFee: Int, val totalPrice: Int) :
        OrderCommonListModel
}

sealed interface OrderSuccessListModel {
    data class Item(val historyItem: HistoryItem) : OrderSuccessListModel
}