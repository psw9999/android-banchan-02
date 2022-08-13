package com.example.banchan.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BasketItem(
    @PrimaryKey
    val hash: String,
    val count: Int,
    val isSelected: Boolean
)