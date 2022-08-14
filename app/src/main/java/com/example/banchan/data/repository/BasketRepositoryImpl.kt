package com.example.banchan.data.repository

import com.example.banchan.data.source.local.BasketItem
import com.example.banchan.data.source.local.BasketLocalDataSource
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val localDataSource: BasketLocalDataSource
) : BasketRepository {
    override fun getBasketItems() = localDataSource.getBasketItems()

    override suspend fun insertBasketItem(vararg basketItem: BasketItem) {
        localDataSource.insertBasketItem(*basketItem)
    }

    override suspend fun updateBasketItem(vararg basketItem: BasketItem) {
        localDataSource.updateBasketItem(*basketItem)
    }
}