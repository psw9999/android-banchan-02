package com.example.banchan.domain.model

data class RecentlyProductModel(
    val description: String,
    val detailHash: String,
    val image: String,
    val discountPercent: String?,
    val discountPrice: String? = null,
    val originPrice: String,
    val title: String,
    val time: String,
    val isCartAdded: Boolean = false,
)