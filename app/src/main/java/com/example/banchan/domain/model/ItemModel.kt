package com.example.banchan.domain.model

import androidx.annotation.StringRes
import com.example.banchan.R
import com.example.banchan.presentation.adapter.main.Type

data class ItemModel(
    val description: String,
    val detailHash: String,
    val image: String,
    val discountPercent: String?,
    val discountPrice: String? = null,
    val originPrice: String,
    val title: String,
    val isCartAdded: Boolean = false
)