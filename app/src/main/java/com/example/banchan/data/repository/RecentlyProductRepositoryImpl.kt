package com.example.banchan.data.repository

import com.example.banchan.data.source.RecentlyProductDataSource
import com.example.banchan.data.source.local.recent.RecentlyProduct
import javax.inject.Inject

class RecentlyProductRepositoryImpl @Inject constructor(
    private val localDataSource: RecentlyProductDataSource
) : RecentlyProductRepository {
    override fun getRecentlyProducts() = localDataSource.getRecentlyProducts()

    override suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit> =
        runCatching {
            localDataSource.insertRecentlyProduct(*recentlyProduct)
        }

    override suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit> =
        runCatching {
            localDataSource.updateRecentlyProduct(*recentlyProduct)
        }
}