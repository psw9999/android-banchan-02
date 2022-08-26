package com.example.banchan.presentation.recentlyproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.domain.usecase.recently.GetRecentProductPagingUseCase
import com.example.banchan.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecentlyProductViewModel @Inject constructor(
    getRecentProductUseCase: GetRecentProductPagingUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Boolean>> =
        MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState<Boolean>>
        get() = _uiState

    val pagingData = getRecentProductUseCase().map { pagingData ->
        pagingData.map {
            val result = it.getOrNull()
            if (result != null) {
                _uiState.update { UiState.Success(true) }
                RecentUiModel.Success(result)
            } else {
                _uiState.update { UiState.Error(Exception()) }
                RecentUiModel.Error
            }
        }
    }.cachedIn(viewModelScope)
}

sealed interface RecentUiModel {
    data class Success(val itemModel: ItemModel) : RecentUiModel
    object Error : RecentUiModel
}