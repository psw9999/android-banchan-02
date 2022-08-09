package com.example.banchan.data.response

data class ItemModel(
    val id: Int,
    val description: String,
    val detailHash: String,
    val image: String,
    val discountPercent: String?,
    val discountPrice: String? = null,
    val originPrice: String,
    val title: String,
    val isCartAdded: Boolean = false
)