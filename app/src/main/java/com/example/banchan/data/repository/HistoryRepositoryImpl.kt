package com.example.banchan.data.repository

import com.example.banchan.data.source.HistoryDataSource
import com.example.banchan.data.source.local.history.History
import com.example.banchan.data.source.local.history.HistoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localDataSource: HistoryDataSource
) : HistoryRepository {

    override fun getHistoryList(): Flow<Result<List<History>>> = localDataSource.getHistoryList()

    override fun getHistoryWithItems() = localDataSource.getHistoryWithItems()

    override fun getHistoryWithItemsById(id: Long) = localDataSource.getHistoryWithItemsById(id)

    override suspend fun insertHistoryWithItems(items: List<HistoryItem>, deliveryFee: Int) =
        localDataSource.insertHistoryWithItems(items, deliveryFee)

    override suspend fun updateHistory(id: Long) {
        localDataSource.updateHistory(id)
    }
}