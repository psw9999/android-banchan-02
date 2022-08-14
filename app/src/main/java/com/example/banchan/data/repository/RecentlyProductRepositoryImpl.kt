package com.example.banchan.data.repository

import com.example.banchan.data.source.RecentlyProductDataSource
import com.example.banchan.data.source.local.recent.RecentlyProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentlyProductRepositoryImpl @Inject constructor(
    private val localDataSource: RecentlyProductDataSource
) : RecentlyProductRepository {
    override fun getRecentlyProducts(): Flow<List<RecentlyProduct>> =
        localDataSource.getRecentlyProducts()

    override suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct) {
        localDataSource.insertRecentlyProduct(*recentlyProduct)
    }

    override suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct) {
        localDataSource.updateRecentlyProduct(*recentlyProduct)
    }
}