package com.example.banchan.presentation

sealed class UiState<out T> {
    object Init: UiState<Nothing>()
    object Loading: UiState<Nothing>()
    class Success<out T>(val item: T): UiState<T>()
    class Error(val throwable: Throwable?) : UiState<Nothing>()
    object Empty: UiState<Nothing>()
}