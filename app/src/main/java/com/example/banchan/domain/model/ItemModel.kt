package com.example.banchan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(
    val description: String,
    val detailHash: String,
    val image: String,
    val discountPercent: String?,
    val discountPrice: String? = null,
    val originPrice: String,
    val title: String,
    val isCartAdded: Boolean = false,
    val time: String = "",
    val originTime: Long = 0L
) : Parcelable