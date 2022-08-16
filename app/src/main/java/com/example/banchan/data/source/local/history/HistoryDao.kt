package com.example.banchan.data.source.local.history

import androidx.room.*
import com.example.banchan.util.DEFAULT_DELIVERY_TIME
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HistoryDao {
    @Query("select * from history")
    fun getHistoryWithItems(): Flow<List<HistoryWithItems>>

    @Query("select * from history where history.id = :id")
    fun getHistoryWithItemsById(id: Int): Flow<HistoryWithItems>

    @Insert
    suspend fun insertHistory(history: History): Long

    @Query("select * from history")
    suspend fun getAllHistory(): List<History>

    @Update
    suspend fun updateHistory(history: History)

    @Transaction
    suspend fun updateAllHistory(time: Int) {
        val history = getAllHistory()
        history.forEach {
            val calTime =
                DEFAULT_DELIVERY_TIME - ((Date().time - it.date.time) / (1000 * 60)).toInt()
            val remainTime = if (calTime >= 0) calTime else 0
            updateHistory(it.copy(remainTime = remainTime))
        }
    }

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