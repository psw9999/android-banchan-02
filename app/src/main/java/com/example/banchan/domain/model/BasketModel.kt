package com.example.banchan.domain.model

data class BasketModel(
    val detailHash : String,
    val count : Int,
    val isChecked : Boolean = true,
    val name : String,
    val price : String,
    val image : String
)