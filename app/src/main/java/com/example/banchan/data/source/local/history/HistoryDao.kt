package com.example.banchan.data.source.local.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("select * from history")
    fun getHistoryWithItems(): Flow<List<HistoryWithItems>>

    @Query("select * from history where history.id = :id")
    fun getHistoryWithItemsById(id: Long): Flow<HistoryWithItems>

    @Insert
    suspend fun insertHistory(history: History): Long

    @Query("select * from history")
    suspend fun getHistoryList(): List<History>

    @Query("select * from history order by id desc")
    fun getHistoryStream(): Flow<List<History>>
    
    @Query("UPDATE history SET isSuccess = :isSuccess WHERE history.id = :id ")
    suspend fun updateHistory(id: Long, isSuccess: Boolean)

    @Insert
    suspend fun insertHistoryItem(vararg historyItem: HistoryItem)

    @Transaction
    suspend fun insertHistoryWithItems(historyItem: List<HistoryItem>, deliveryFee: Int): Long {
        val id = insertHistory(History(deliveryFee = deliveryFee))
        historyItem.forEach {
            insertHistoryItem(it.copy(historyId = id))
        }

        return id
    }
}