package com.example.banchan.data.source.local.recent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class RecentlyProduct(
    @PrimaryKey
    val hash: String,
    val name: String,
    val recentlyTime: Date
)