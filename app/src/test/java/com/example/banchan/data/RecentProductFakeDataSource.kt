package com.example.banchan.data

import androidx.paging.PagingSource
import com.example.banchan.data.source.RecentlyProductDataSource
import com.example.banchan.data.source.local.recent.RecentlyProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class RecentProductFakeDataSource : RecentlyProductDataSource {
    private val testRecentProductStream = MutableSharedFlow<List<RecentlyProduct>>()

    override fun getRecentlyProductPagingData(): PagingSource<Int, RecentlyProduct> {
        TODO("Not yet implemented")
    }

    override fun getRecentlyProducts(): Flow<Result<List<RecentlyProduct>>> {
        return testRecentProductStream.map { Result.success(it) }
    }

    override suspend
    fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit> {
        return kotlin.runCatching {
            testRecentProductStream.emit(recentlyProduct.toList())
        }
    }

    override suspend
    fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit> {
        TODO("Not yet implemented")
    }
}