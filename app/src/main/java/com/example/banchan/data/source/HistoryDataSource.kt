package com.example.banchan.data.source

import com.example.banchan.data.source.local.history.History
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.data.source.local.history.HistoryWithItems
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getHistoryWithItems(): Flow<Result<List<HistoryWithItems>>>
    fun getHistoryWithItemsById(id: Int): Flow<Result<HistoryWithItems>>
    suspend fun insertHistoryWithItems(items: List<HistoryItem>, deliveryFee: Int): Result<Unit>
    suspend fun updateAllHistory()
}