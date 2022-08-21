package com.example.banchan.data.repository

import com.example.banchan.data.source.HistoryDataSource
import com.example.banchan.data.source.local.history.HistoryItem
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localDataSource: HistoryDataSource
) : HistoryRepository {
    override fun getHistoryWithItems() = localDataSource.getHistoryWithItems()

    override fun getHistoryWithItemsById(id: Long) = localDataSource.getHistoryWithItemsById(id)

    override suspend fun insertHistoryWithItems(items: List<HistoryItem>, deliveryFee: Int) =
        localDataSource.insertHistoryWithItems(items, deliveryFee)

    override suspend fun updateAllHistory() {
        localDataSource.updateAllHistory()
    }
}