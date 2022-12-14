package com.example.banchan.data.source

import com.example.banchan.data.source.local.history.History
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.data.source.local.history.HistoryWithItems
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getHistoryWithItems(): Flow<Result<List<HistoryWithItems>>>
    fun getHistoryWithItemsById(id: Long): Flow<Result<HistoryWithItems>>
    fun getHistoryStream(): Flow<Result<List<History>>>
    suspend fun getHistoryList(): Result<List<History>>
    suspend fun insertHistoryWithItems(items: List<HistoryItem>, deliveryFee: Int): Result<Long>
    suspend fun updateHistory(id: Long)
}