package com.example.banchan.presentation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.domain.usecase.basket.InsertBasketItemUseCase
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketBottomSheetViewModel @Inject constructor(
    private val insertBasketItemUseCase: InsertBasketItemUseCase
) : ViewModel() {

    private val _isInsertSuccess = MutableSharedFlow<Boolean>()
    val isInsertSuccess: SharedFlow<Boolean> = _isInsertSuccess

    private val _productCount = MutableStateFlow(1)
    val productCount = _productCount.asStateFlow()

    fun productCountDecrease() {
        _productCount.value = _productCount.value.dec()
    }

    fun productCountIncrease() {
        _productCount.value = _productCount.value.inc()
    }

    fun getAmountSum(itemModel: ItemModel, count: Int): Int {
        return if (itemModel.discountPercent == null) {
            (itemModel.originPrice.toNum() * count)
        } else {
            (itemModel.discountPrice.toNum()!! * count)
        }
    }

    fun insertSelectedBasketItem(itemModel: ItemModel) {
        viewModelScope.launch {
            insertBasketItemUseCase.invoke(
                BasketItem(
                    hash = itemModel.detailHash,
                    count = productCount.value,
                    name = itemModel.title,
                    isSelected = true,
                )
            )
                .onSuccess { _isInsertSuccess.emit(true) }
                .onFailure { _isInsertSuccess.emit(false) }
        }
    }

}