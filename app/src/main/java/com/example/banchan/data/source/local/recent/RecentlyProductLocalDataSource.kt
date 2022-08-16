package com.example.banchan.data.source.local.recent

import com.example.banchan.data.source.RecentlyProductDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentlyProductLocalDataSource @Inject constructor(
    private val recentlyProductDao: RecentlyProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecentlyProductDataSource {
    override fun getRecentlyProducts() = recentlyProductDao.getRecentlyProducts().map {
        Result.success(it)
    }.catch { exception ->
        Result.failure<Exception>(exception)
    }

    override suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct) =
        runCatching {
            withContext(ioDispatcher) {
                recentlyProductDao.insertRecentlyProduct(*recentlyProduct)
            }
        }

    override suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct) =
        runCatching {
            withContext(ioDispatcher) {
                recentlyProductDao.updateRecentlyProduct(*recentlyProduct)
            }
        }
}