package com.example.banchan.data.source.local

import com.example.banchan.data.source.BasketDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasketLocalDataSource @Inject constructor(
    private val basketDao: BasketDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BasketDataSource {
    override fun getBasketItems() = basketDao.getBasketItems()

    override suspend fun insertBasketItem(vararg basketItem: BasketItem) {
        withContext(ioDispatcher) {
            basketDao.insertBasketItem(*basketItem)
        }
    }

    override suspend fun updateBasketItem(vararg basketItem: BasketItem) {
        withContext(ioDispatcher) {
            basketDao.updateBasketItem(*basketItem)
        }
    }
}