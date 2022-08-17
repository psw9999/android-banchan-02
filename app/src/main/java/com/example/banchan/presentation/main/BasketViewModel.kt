package com.example.banchan.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.basket.InsertBasketItemUseCase
import com.example.banchan.util.ext.toNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketItemUseCase: GetBasketItemUseCase,
    private val insertBasketItemUseCase: InsertBasketItemUseCase
) : ViewModel() {

    private val _selectedBasketItem = MutableLiveData<ItemModel>(null)
    val selectedBasketItem: LiveData<ItemModel> = _selectedBasketItem
    private val _selectedBasketCount = MutableLiveData<Int>(1)
    val selectedBasketCount: LiveData<Int> = _selectedBasketCount

    val basketFlow = getBasketItemUseCase()

    private val _isInsertSuccess = MutableSharedFlow<Boolean>()
    val isInsertSuccess: SharedFlow<Boolean> = _isInsertSuccess

    fun setSelectedBasketItem(basketItem: ItemModel) {
        _selectedBasketItem.value = basketItem
        _selectedBasketCount.value = 1
    }

    fun basketCountDecrease() {
        _selectedBasketCount.value = _selectedBasketCount.value!!.dec()
    }

    fun basketCountIncrease() {
        _selectedBasketCount.value = _selectedBasketCount.value!!.inc()
    }

    fun getAmountSum(count: Int): Int {
        return if (_selectedBasketItem.value!!.discountPercent == null) {
            (_selectedBasketItem.value!!.originPrice.toNum() * count)
        } else {
            (_selectedBasketItem.value!!.discountPrice.toNum()!! * count)
        }
    }

    fun insertSelectedBasketItem() {
        viewModelScope.launch {
            insertBasketItemUseCase.invoke(
                BasketItem(
                    hash = selectedBasketItem.value!!.detailHash,
                    count = _selectedBasketCount.value!!,
                    isSelected = true,
                )
            )
                .onSuccess { _isInsertSuccess.emit(true) }
                .onFailure { _isInsertSuccess.emit(false) }
        }
    }

}