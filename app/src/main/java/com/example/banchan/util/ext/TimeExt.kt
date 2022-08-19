package com.example.banchan.util.ext

fun Long.toTimeFormat(): String {
    return when {
        this < 60 -> {
            "${this}초전"
        }
        this < 60 * 60 -> {
            "${this / 60}분전"
        }
        this < 60 * 60 * 24 -> {
            "${this / (60 * 60)}시간전"
        }
        else -> {
            "${this / (60 * 60 * 24)}일전"
        }
    }
}