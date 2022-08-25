package com.example.banchan.presentation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.usecase.basket.UpdateBasketItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmountDialogViewModel @Inject constructor(
    private val updateBasketItemUseCase: UpdateBasketItemUseCase
) : ViewModel() {

    private val _amount: MutableStateFlow<Int> = MutableStateFlow(1)
    val amount = _amount.asStateFlow()

    private var _basketModel: BasketModel? = null
    val basketModel
        get() = _basketModel

    fun setAmount(amount: Int) {
        _amount.value = amount
    }

    fun setBasketModel(basketModel: BasketModel) {
        _basketModel = basketModel
    }

    fun updateBasketItemAmount() {
        viewModelScope.launch {
            _basketModel?.let { basketModel ->
                updateBasketItemUseCase.invoke(
                    BasketItem(
                        hash = basketModel.detailHash,
                        name = basketModel.name,
                        count = amount.value,
                        isSelected = basketModel.isChecked,
                    )
                )
            }
        }
    }

}