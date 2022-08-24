package com.example.banchan.presentation.ordersuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.domain.usecase.history.GetHistoryByIdUseCase
import com.example.banchan.presentation.UiState
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

    private val _uiState: MutableStateFlow<UiState<OrderSuccessUiModel>> =
        MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState<OrderSuccessUiModel>>
        get() = _uiState

    init {
        viewModelScope.launch {
            refresh.emit(true)
            combine(history, refresh) { history, _ ->
                history.getOrNull()?.let {
                    val orderPrice = it.items.sumOf { item -> item.originPrice * item.count }
                    OrderSuccessUiModel(
                        OrderSuccessListModel.Header(
                            time = if (it.history.isSuccess) 0 else (DEFAULT_DELIVERY_TIME - (Date().time - it.history.date.time) / (1000 * 60)).toInt(),
                            count = it.items.size
                        ),
                        OrderSuccessListModel.Body(it.items),
                        OrderSuccessListModel.Footer(
                            orderPrice = orderPrice,
                            deliveryFee = it.history.deliveryFee,
                            totalPrice = orderPrice + DEFAULT_DELIVERY_FEE
                        )
                    )
                }
            }.collect { uiModel ->
                uiModel?.let {
                    _uiState.update {
                        UiState.Success(uiModel)
                    }
                }
            }
        }
    }

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

sealed interface OrderSuccessListModel {
    data class Header(val time: Int, val count: Int) : OrderSuccessListModel
    data class Body(val historyItem: List<HistoryItem>) : OrderSuccessListModel
    data class Footer(val orderPrice: Int, val deliveryFee: Int, val totalPrice: Int) :
        OrderSuccessListModel
}

data class OrderSuccessUiModel(
    val header: OrderSuccessListModel.Header,
    val body: OrderSuccessListModel.Body,
    val footer: OrderSuccessListModel.Footer
)