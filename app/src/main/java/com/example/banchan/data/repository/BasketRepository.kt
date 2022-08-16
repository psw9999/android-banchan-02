package com.example.banchan.data.repository

import com.example.banchan.data.source.local.basket.BasketItem
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun getBasketItems(): Flow<Result<List<BasketItem>>>
    suspend fun insertBasketItem(vararg basketItem: BasketItem): Result<Unit>
    suspend fun updateBasketItem(vararg basketItem: BasketItem): Result<Unit>
}