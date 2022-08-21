package com.example.banchan.presentation.orderlist

import androidx.lifecycle.ViewModel
import com.example.banchan.data.source.local.history.History
import com.example.banchan.domain.model.OrderListModel
import com.example.banchan.domain.usecase.history.GetHistoryByIdUseCase
import com.example.banchan.domain.usecase.history.GetHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    getHistoryListUseCase: GetHistoryListUseCase,
    private val getHistoryByIdUseCase: GetHistoryByIdUseCase
) : ViewModel() {

    private val historyListFlow: Flow<List<History>> =
        getHistoryListUseCase().map { result ->
            result.onSuccess { historyList ->
                return@map historyList
            }
            listOf()
        }

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
                                price = history.deliveryFee + historyWithItems.items.sumOf { it.originPrice * it.count},
                                isCompleted = history.remainTime == 0
                            )
                        )
                    }
                }
            }
            historyModelList
        }

}