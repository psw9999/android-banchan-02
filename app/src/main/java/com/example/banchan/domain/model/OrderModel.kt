package com.example.banchan.domain.model

data class OrderModel(
    val orderPrice: Int,
    val deliveryFee: Int = 2500,
    val minOrderPrice: Int = 10000,
    val freeOrderPrice: Int = 40000
)