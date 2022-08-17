package com.example.banchan.presentation.home.best

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.BestListItem
import com.example.banchan.domain.model.BestModel
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.home.GetBestDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestDishesUseCase: GetBestDishesUseCase,
    private val getBasketItemUseCase: GetBasketItemUseCase
) : ViewModel() {

    private val _bestDishes =
        MutableStateFlow(listOf(BestListItem.BestHeader(), BestListItem.BestLoading))
    val bestDishes: StateFlow<List<BestListItem>> = _bestDishes

    val bestListItems: Flow<List<BestListItem>> =
        combine(bestDishes, getBasketItemUseCase()) { bestDishes, basketList ->
            basketList.onSuccess {
                return@combine checkIsCartAdded(bestDishes, it)
            }
            bestDishes
        }

    init {
        getBestDishes()
    }

    fun getBestDishes() {
        _bestDishes.value = listOf(BestListItem.BestHeader(),BestListItem.BestLoading)
        viewModelScope.launch {
            getBestDishesUseCase().let { result ->
                val list = arrayListOf<BestListItem>(BestListItem.BestHeader())
                if (result.isSuccess) {
                    if (result.getOrThrow().isEmpty()) list.add(BestListItem.BestEmpty)
                    else {
                        result.getOrThrow().map {
                            list.add(BestListItem.BestContent(it))
                        }
                    }
                    _bestDishes.emit(list)
                } else {
                    list.add(BestListItem.BestError)
                }
            }
        }
    }

    private fun checkIsCartAdded(
        bestDishes: List<BestListItem>,
        basketList: List<BasketItem>
    ): List<BestListItem> =
        bestDishes.map { dish ->
            if (dish is BestListItem.BestContent) {
                dish.copy(
                    bestItem = BestModel(
                        title = dish.bestItem.title,
                        items = dish.bestItem.items.map { model ->
                            model.copy(
                                isCartAdded = model.detailHash in basketList.map { it.hash }
                            )
                        }
                    )
                )
            }
            else {
                dish
            }
        }

}