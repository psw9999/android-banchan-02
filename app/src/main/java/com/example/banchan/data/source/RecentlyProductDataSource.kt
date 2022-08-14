package com.example.banchan.data.source

import com.example.banchan.data.source.local.recent.RecentlyProduct
import kotlinx.coroutines.flow.Flow

interface RecentlyProductDataSource {
    fun getRecentlyProducts(): Flow<List<RecentlyProduct>>
    suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct)
    suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct)
}