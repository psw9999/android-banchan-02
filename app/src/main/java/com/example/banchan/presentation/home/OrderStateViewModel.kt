package com.example.banchan.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.history.History
import com.example.banchan.domain.usecase.history.GetHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrderStateViewModel @Inject constructor(
    getHistoryListUseCase: GetHistoryListUseCase
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
}