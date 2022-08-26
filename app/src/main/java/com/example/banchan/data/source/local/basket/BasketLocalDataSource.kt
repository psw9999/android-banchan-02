package com.example.banchan.data.source.local.basket

import com.example.banchan.data.source.BasketDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasketLocalDataSource @Inject constructor(
    private val basketDao: BasketDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BasketDataSource {
    override fun getBasketItems() = basketDao.getBasketItems().map {
        Result.success(it)
    }.catch { exception ->
        Result.failure<Exception>(exception)
    }

    override suspend fun insertBasketItem(vararg basketItem: BasketItem) = runCatching {
        withContext(ioDispatcher) {
            basketDao.insertBasketItem(*basketItem)
        }
    }

    override suspend fun updateBasketItem(vararg basketItem: BasketItem) = runCatching {
        withContext(ioDispatcher) {
            basketDao.updateBasketItem(*basketItem)
        }
    }

    override suspend fun updateAllBasketIsSelected(isSelected: Int) = runCatching {
        withContext(ioDispatcher) {
            basketDao.updateAllBasketIsSelected(isSelected)
        }
    }

    override suspend fun deleteBasketItem(vararg basketItem: BasketItem) = runCatching {
        withContext(ioDispatcher) {
            basketDao.deleteBasketItem(*basketItem)
        }
    }

    override suspend fun deleteSelectedBasketItem() = runCatching {
        withContext(ioDispatcher) {
            basketDao.deleteSelectedBasketItem()
        }
    }

}