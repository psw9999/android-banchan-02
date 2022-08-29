package com.example.banchan.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.history.History
import com.example.banchan.domain.model.OrderListModel
import com.example.banchan.domain.usecase.history.GetHistoryByIdUseCase
import com.example.banchan.domain.usecase.history.GetHistoryStreamUseCase
import com.example.banchan.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrderStateViewModel @Inject constructor(
    getHistoryStreamUseCase: GetHistoryStreamUseCase,
    private val getHistoryByIdUseCase: GetHistoryByIdUseCase
) : ViewModel() {

    private val historyListFlow: Flow<List<History>?> =
        getHistoryStreamUseCase().map { result ->
            result.getOrNull()
        }

    val uiState: StateFlow<UiState<List<OrderListModel>>> =
        historyListFlow.map { historyList ->
            if (historyList == null) return@map UiState.Error(Throwable("DB Error"))
            else {
                if (historyList.isEmpty()) return@map UiState.Empty
                else {
                    return@map UiState.Success(toHistoryModelList(historyList))
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Init
        )

    private suspend fun toHistoryModelList(historyList: List<History>): List<OrderListModel> {
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
        return historyModelList
    }

}