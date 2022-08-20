package com.example.banchan.domain.model

data class OrderListModel(
    val id: Long,
    val thumbNailImage : String,
    val price: Int,
    val name: String,
    val numberOfProduct: Int,
    val isCompleted: Boolean
)