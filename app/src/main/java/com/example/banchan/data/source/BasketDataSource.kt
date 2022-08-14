package com.example.banchan.data.source

import com.example.banchan.data.source.local.basket.BasketItem
import kotlinx.coroutines.flow.Flow

interface BasketDataSource {
    fun getBasketItems(): Flow<List<BasketItem>>
    suspend fun insertBasketItem(vararg basketItem: BasketItem)
    suspend fun updateBasketItem(vararg basketItem: BasketItem)
}