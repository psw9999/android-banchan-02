package com.example.banchan.domain.model

data class BasketModel(
    val detailHash : String,
    val count : Int,
    val isChecked : Boolean = true,
    val name : String,
    val price : Int,
    val image : String
)