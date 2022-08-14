package com.example.banchan.data.source.local.recent

import com.example.banchan.data.source.RecentlyProductDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentlyProductLocalDataSource @Inject constructor(
    private val recentlyProductDao: RecentlyProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecentlyProductDataSource {
    override fun getRecentlyProducts(): Flow<List<RecentlyProduct>> =
        recentlyProductDao.getRecentlyProducts()

    override suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct) {
        withContext(ioDispatcher) {
            recentlyProductDao.insertRecentlyProduct(*recentlyProduct)
        }
    }

    override suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct) {
        withContext(ioDispatcher) {
            recentlyProductDao.updateRecentlyProduct(*recentlyProduct)
        }
    }
}