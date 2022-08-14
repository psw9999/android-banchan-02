package com.example.banchan.data.repository

import com.example.banchan.data.source.HistoryDataSource
import com.example.banchan.data.source.local.history.HistoryItem
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localDataSource: HistoryDataSource
) : HistoryRepository {
    override fun getHistoryWithItems() = localDataSource.getHistoryWithItems()

    override suspend fun insertHistoryWithItems(items: List<HistoryItem>) {
        localDataSource.insertHistoryWithItems(items)
    }
}