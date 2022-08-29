package com.example.banchan.data.source.local.basket

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BasketItem(
    @PrimaryKey
    val hash: String,
    val count: Int,
    val name: String,
    val isSelected: Boolean,
    val time: Long = Date().time
)