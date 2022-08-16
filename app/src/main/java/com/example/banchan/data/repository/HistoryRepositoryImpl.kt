package com.example.banchan.data.repository

import com.example.banchan.data.source.HistoryDataSource
import com.example.banchan.data.source.local.history.HistoryItem
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localDataSource: HistoryDataSource
) : HistoryRepository {
    override fun getHistoryWithItems() = localDataSource.getHistoryWithItems()

    override fun getHistoryWithItemsById(id: Int) = localDataSource.getHistoryWithItemsById(id)

    override suspend fun insertHistoryWithItems(items: List<HistoryItem>): Result<Unit> =
        runCatching {
            localDataSource.insertHistoryWithItems(items)
        }

    override suspend fun updateAllHistory() {
        localDataSource.updateAllHistory()
    }
}