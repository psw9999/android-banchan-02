package com.example.banchan.presentation.recentlyproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.domain.usecase.recently.GetRecentProductUseCase
import com.example.banchan.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyProductViewModel @Inject constructor(
    private val getRecentProductUseCase: GetRecentProductUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ItemModel>>> =
        MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState<List<ItemModel>>>
        get() = _uiState

    init {
        viewModelScope.launch {
            getRecentProductUseCase.refresh()
            getRecentProductUseCase().collect {
                _uiState.update { _ ->
                    if (it == null) return@update UiState.Error(Exception(""))
                    if (it.isEmpty()) UiState.Empty else UiState.Success(it)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            getRecentProductUseCase.refresh()
        }
    }
}