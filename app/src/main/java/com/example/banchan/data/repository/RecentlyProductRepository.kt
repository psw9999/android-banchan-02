package com.example.banchan.data.repository

import androidx.paging.PagingData
import com.example.banchan.data.source.local.recent.RecentlyProduct
import kotlinx.coroutines.flow.Flow

interface RecentlyProductRepository {
    fun getRecentlyProducts(): Flow<Result<List<RecentlyProduct>>>
    fun getRecentlyProductPagingData(): Flow<PagingData<RecentlyProduct>>
    suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit>
    suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit>
}