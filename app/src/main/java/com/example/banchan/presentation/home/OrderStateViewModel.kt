package com.example.banchan.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.history.History
import com.example.banchan.domain.model.OrderListModel
import com.example.banchan.domain.usecase.history.GetHistoryByIdUseCase
import com.example.banchan.domain.usecase.history.GetHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrderStateViewModel @Inject constructor(
    getHistoryListUseCase: GetHistoryListUseCase,
    private val getHistoryByIdUseCase: GetHistoryByIdUseCase
) : ViewModel() {

    private val historyListFlow: Flow<List<History>> =
        getHistoryListUseCase().map { result ->
            result.getOrDefault(listOf())
        }

    val isOrderingStateFlow: StateFlow<Boolean> =
        historyListFlow.map { historyList ->
            historyList.all { it.isSuccess }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val historyModelFlow: Flow<List<OrderListModel>> =
        historyListFlow.map { historyList ->
            val historyModelList = mutableListOf<OrderListModel>()
            historyList.forEach { history ->
                getHistoryByIdUseCase(history.id).first().onSuccess { historyWithItems ->
                    if (historyWithItems.items.isNotEmpty()) {
                        historyModelList.add(
                            OrderListModel(
                                id = history.id,
                                thumbNailImage = historyWithItems.items[0].imageUrl,
                                name = historyWithItems.items[0].name,
                                numberOfProduct = historyWithItems.items.size,
                                price = history.deliveryFee + historyWithItems.items.sumOf { it.originPrice * it.count },
                                isCompleted = history.isSuccess
                            )
                        )
                    }
                }
            }
            historyModelList
        }
}