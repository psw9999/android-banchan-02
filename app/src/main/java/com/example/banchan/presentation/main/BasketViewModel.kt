package com.example.banchan.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.util.ext.toNum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BasketViewModel : ViewModel() {

    private val _selectedBasketItem = MutableLiveData<ItemModel>(null)
    val selectedBasketItem: LiveData<ItemModel> = _selectedBasketItem
    private val _selectedBasketCount = MutableLiveData<Int>(1)
    val selectedBasketCount: LiveData<Int> = _selectedBasketCount

    private val _basketList = MutableStateFlow<List<BasketModel>>(listOf())
    val basketList: StateFlow<List<BasketModel>> = _basketList

    fun setSelectedBasketItem(basketItem : ItemModel) {
        _selectedBasketItem.value = basketItem
        _selectedBasketCount.value = 1
    }

    fun basketCountDecrease() {
        _selectedBasketCount.value = _selectedBasketCount.value!!.dec()
    }

    fun basketCountIncrease() {
        _selectedBasketCount.value = _selectedBasketCount.value!!.inc()
    }

    fun getAmountSum(count : Int) : Int {
        return if (_selectedBasketItem.value!!.discountPercent == null) {
            (_selectedBasketItem.value!!.originPrice.toNum()!! * count)
        } else {
            (_selectedBasketItem.value!!.discountPrice.toNum()!! * count)
        }
    }

    fun addBasketList(basketModel : BasketModel) {
        val list = _basketList.value + basketModel
        _basketList.value = list
    }

}