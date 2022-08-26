package com.example.banchan.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.banchan.data.source.RecentlyProductDataSource
import com.example.banchan.data.source.local.recent.RecentlyProduct
import javax.inject.Inject

class RecentlyProductRepositoryImpl @Inject constructor(
    private val localDataSource: RecentlyProductDataSource
) : RecentlyProductRepository {
    override fun getRecentlyProducts() = localDataSource.getRecentlyProducts()

    override fun getRecentlyProductPagingData() =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            localDataSource.getRecentlyProductPagingData()
        }.flow

    override suspend fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit> =
        runCatching {
            localDataSource.insertRecentlyProduct(*recentlyProduct)
        }

    override suspend fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct): Result<Unit> =
        runCatching {
            localDataSource.updateRecentlyProduct(*recentlyProduct)
        }

    companion object {
        private const val PAGE_SIZE = 30
    }
}