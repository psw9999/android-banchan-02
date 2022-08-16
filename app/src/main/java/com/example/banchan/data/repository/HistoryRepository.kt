package com.example.banchan.data.repository

import com.example.banchan.data.source.local.history.History
import com.example.banchan.data.source.local.history.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryWithItems(): Flow<Result<Map<History, List<HistoryItem>>>>
    suspend fun insertHistoryWithItems(items: List<HistoryItem>): Result<Unit>
}