package com.example.banchan.data.source

import com.example.banchan.data.source.local.recent.RecentlyProduct
import kotlinx.coroutines.flow.Flow

interface RecentlyProductDataSource {
    fun getRecentlyProducts(): Flow<Result<List<RecentlyProduct>>>
    suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit>
    suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit>
}