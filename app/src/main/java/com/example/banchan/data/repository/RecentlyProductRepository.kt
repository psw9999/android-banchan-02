package com.example.banchan.data.repository

import com.example.banchan.data.source.local.recent.RecentlyProduct
import kotlinx.coroutines.flow.Flow

interface RecentlyProductRepository {
    fun getRecentlyProducts(): Flow<Result<List<RecentlyProduct>>>
    suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct)
    suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct)
}