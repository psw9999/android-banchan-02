package com.example.banchan.presentation.home.best

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.domain.model.BestListItem
import com.example.banchan.domain.usecase.GetBestDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestDishesUseCase: GetBestDishesUseCase
) : ViewModel() {

    private val _bestDishes =
        MutableStateFlow(listOf(BestListItem.BestHeader(), BestListItem.BestLoading))
    val bestDishes: StateFlow<List<BestListItem>> = _bestDishes

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

}