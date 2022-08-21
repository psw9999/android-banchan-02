package com.example.banchan.data.repository

import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.data.source.local.history.HistoryWithItems
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryWithItems(): Flow<Result<List<HistoryWithItems>>>
    fun getHistoryWithItemsById(id: Long): Flow<Result<HistoryWithItems>>
    suspend fun insertHistoryWithItems(items: List<HistoryItem>, deliveryFee: Int): Result<Long>
    suspend fun updateAllHistory()
}