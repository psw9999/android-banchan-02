package com.example.banchan.presentation.home.best

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.BestModel
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.home.GetBestDishesUseCase
import com.example.banchan.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestDishesUseCase: GetBestDishesUseCase,
    private val getBasketItemUseCase: GetBasketItemUseCase
) : ViewModel() {

    private val refresh = MutableSharedFlow<Boolean>(replay = 1)

    private val _bestUiState: MutableStateFlow<UiState<List<BestModel>>> =
        MutableStateFlow(UiState.Init)
    val bestUiState = _bestUiState.asStateFlow()

    private val _bestDishes = refresh.map { _ ->
        _bestUiState.update{ return@update UiState.Loading }
        getBestDishesUseCase().getOrNull()
    }

    init {
        viewModelScope.launch {
            refresh.emit(true)
            combine(_bestDishes, getBasketItemUseCase()) { _, basketItems ->
                checkIsCartAdded(getBestDishesUseCase().getOrNull(), basketItems.getOrDefault(listOf()))
            }.collect { bestList ->
                _bestUiState.update {
                    if(bestList == null) return@update UiState.Error(Exception(""))
                    if(bestList.isEmpty()) UiState.Empty else UiState.Success(bestList)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refresh.emit(true)
        }
    }

    private fun checkIsCartAdded(
        bestDishes: List<BestModel>?,
        basketList: List<BasketItem>
    ): List<BestModel>? =
        bestDishes?.map { dish ->
            dish.copy(
                title = dish.title,
                items = dish.items.map { model ->
                    model.copy(
                        isCartAdded = model.detailHash in basketList.map { it.hash }
                    )
                }
            )
        }

}