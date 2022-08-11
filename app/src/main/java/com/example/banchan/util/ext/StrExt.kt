package com.example.banchan.util.ext

fun String?.toNum() = this?.replace("[^0-9]".toRegex(), "")?.toInt()