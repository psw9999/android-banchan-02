package com.example.banchan.data.source.local.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Date = Date(),
    val remainTime: Int = 20
)