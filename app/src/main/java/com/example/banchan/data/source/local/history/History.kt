package com.example.banchan.data.source.local.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.banchan.util.DEFAULT_DELIVERY_TIME
import java.util.*

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date = Date(),
    val deliveryFee: Int = 0,
    val isSuccess: Boolean = false
)