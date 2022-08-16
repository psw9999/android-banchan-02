package com.example.banchan.data.source.local.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0,
    val historyId: Long = 0,
    val imageUrl: String,
    val count: Int,
    val originPrice: Int,
    val name: String
)