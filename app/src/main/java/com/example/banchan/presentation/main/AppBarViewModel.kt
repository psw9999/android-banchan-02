package com.example.banchan.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.history.GetHistoryStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AppBarViewModel @Inject constructor(
    getBasketItemUseCase: GetBasketItemUseCase,
    getHistoryStreamUseCase: GetHistoryStreamUseCase
) : ViewModel() {

    val basketCount : StateFlow<Int> =
        getBasketItemUseCase()
            .map{ result ->
                result.getOrDefault(listOf()).size
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )

    val isOrderingStateFlow: StateFlow<Boolean> =
        getHistoryStreamUseCase().map { historyList ->
            historyList.getOrNull()?.all { it.isSuccess } ?: return@map true
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

}