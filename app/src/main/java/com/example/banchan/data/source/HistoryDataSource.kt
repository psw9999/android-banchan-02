package com.example.banchan.data.source

import com.example.banchan.data.source.local.history.History
import com.example.banchan.data.source.local.history.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getHistoryWithItems(): Flow<Map<History, List<HistoryItem>>>
    suspend fun insertHistoryWithItems(items: List<HistoryItem>)
}