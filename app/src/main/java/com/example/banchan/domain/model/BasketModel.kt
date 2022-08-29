package com.example.banchan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasketModel(
    val detailHash: String,
    val count: Int,
    val isChecked: Boolean = true,
    val name: String,
    val price: Int,
    val image: String,
    val time: Long,
) : Parcelable