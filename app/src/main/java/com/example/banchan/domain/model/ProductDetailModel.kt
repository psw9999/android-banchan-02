package com.example.banchan.domain.model

data class ProductDetailModel(
    val detailHash: String,
    val name: String,
    val point: Int,
    val discountPercent: Int?,
    val originPrice: Int,
    val discountPrice: Int?,
    val deliveryFee: String,
    val description: String,
    val thumbImages: List<String>,
    val detailSection: List<String>,
    val deliveryInfo: String
)