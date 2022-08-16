package com.example.banchan.data.source

import com.example.banchan.data.source.local.basket.BasketItem
import kotlinx.coroutines.flow.Flow

interface BasketDataSource {
    fun getBasketItems(): Flow<Result<List<BasketItem>>>
    suspend fun insertBasketItem(vararg basketItem: BasketItem): Result<Unit>
    suspend fun updateBasketItem(vararg basketItem: BasketItem): Result<Unit>
}