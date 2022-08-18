package com.example.banchan.domain.model

data class RecentlyProductModel(
    val detailHash : String,
    val image : String,
    val name : String,
    val discountPrice : Int?,
    val originPrice : Int,
    val viewedTime : String
)