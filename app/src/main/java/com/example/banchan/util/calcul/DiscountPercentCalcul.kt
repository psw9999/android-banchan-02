package com.example.banchan.util.calcul

fun calculationPercent(originPriceStr : String, discountPriceStr : String) : Int {
    val numberRegex = Regex("[^0-9]")
    val originPrice = originPriceStr.replace(numberRegex, "").toDouble()
    val discountPrice = discountPriceStr.replace(numberRegex, "").toDouble()
    return (((originPrice-discountPrice) / originPrice) * 100.0).toInt()
}