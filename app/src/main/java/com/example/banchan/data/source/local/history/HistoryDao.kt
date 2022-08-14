package com.example.banchan.data.source.local.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("select * from history left join historyitem on historyitem.historyId = history.id")
    fun getHistoryWithItems(): Flow<Map<History, List<HistoryItem>>>

    @Insert
    suspend fun insertHistory(history: History): Long

    @Insert
    suspend fun insertHistoryItem(vararg historyItem: HistoryItem)

    @Transaction
    suspend fun insertHistoryWithItems(historyItem: List<HistoryItem>) {
        val id = insertHistory(History(id = 0))
        historyItem.forEach {
            insertHistoryItem(it.copy(historyId = id))
        }
    }
}