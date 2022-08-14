package com.example.banchan.data.repository

import com.example.banchan.data.source.local.BasketItem
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun getBasketItems(): Flow<List<BasketItem>>
    suspend fun insertBasketItem(vararg basketItem: BasketItem)
    suspend fun updateBasketItem(vararg basketItem: BasketItem)
}