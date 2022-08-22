package com.example.banchan.presentation.ordersuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.domain.usecase.history.GetHistoryByIdUseCase
import com.example.banchan.util.DEFAULT_DELIVERY_FEE
import com.example.banchan.util.DEFAULT_DELIVERY_TIME
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.*

class OrderSuccessViewModel @AssistedInject constructor(
    @Assisted id: Long,
    getHistoryByIdUseCase: GetHistoryByIdUseCase
) : ViewModel() {
    val history = getHistoryByIdUseCase(id)
    private val refresh = MutableSharedFlow<Boolean>(replay = 1)
    val headerUiState = combine(history, refresh) { history, _ ->
        history.getOrNull()?.let {
            OrderCommonListModel.Header(
                time = if (it.history.isSuccess) 0 else (DEFAULT_DELIVERY_TIME - (Date().time - it.history.date.time) / (1000 * 60)).toInt(),
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
            val orderPrice = it.items.sumOf { item -> item.originPrice * item.count }
            OrderCommonListModel.Footer(
                orderPrice = orderPrice,
                deliveryFee = DEFAULT_DELIVERY_FEE,
                totalPrice = orderPrice + DEFAULT_DELIVERY_FEE
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderCommonListModel.Footer(0, 0, 0)
    )

    fun refresh() {
        viewModelScope.launch {
            refresh.emit(true)
        }
    }

    init {
        viewModelScope.launch {
            while (isActive) {
                refresh.emit(true)
                delay(1000 * 60)
            }
        }
    }

    @AssistedFactory
    interface IdAssistedFactory {
        fun create(
            @Assisted id: Long
        ): OrderSuccessViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: IdAssistedFactory,
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}

sealed interface OrderCommonListModel {
    data class Header(val time: Int, val count: Int) : OrderCommonListModel
    data class Footer(val orderPrice: Int, val deliveryFee: Int, val totalPrice: Int) :
        OrderCommonListModel
}

sealed interface OrderSuccessListModel {
    data class Item(val historyItem: HistoryItem) : OrderSuccessListModel
}