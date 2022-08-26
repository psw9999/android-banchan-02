package com.example.banchan.data.repository

import com.example.banchan.data.source.BasketDataSource
import com.example.banchan.data.source.local.basket.BasketItem
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val localDataSource: BasketDataSource
) : BasketRepository {
    override fun getBasketItems() = localDataSource.getBasketItems()

    override suspend fun insertBasketItem(vararg basketItem: BasketItem): Result<Unit> = runCatching {
        localDataSource.insertBasketItem(*basketItem)
    }

    override suspend fun updateBasketItem(vararg basketItem: BasketItem): Result<Unit> = runCatching {
        localDataSource.updateBasketItem(*basketItem)
    }

    override suspend fun updateAllBasketIsSelected(isSelected: Int): Result<Unit> = runCatching {
        localDataSource.updateAllBasketIsSelected(isSelected)
    }

    override suspend fun deleteBasketItem(vararg basketItem: BasketItem): Result<Unit> = runCatching {
        localDataSource.deleteBasketItem(*basketItem)
    }

    override suspend fun deleteSelectedBasketItem(): Result<Unit> = runCatching {
        localDataSource.deleteSelectedBasketItem()
    }

}