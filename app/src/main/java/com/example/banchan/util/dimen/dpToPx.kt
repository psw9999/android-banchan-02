package com.example.banchan.util.dimen

import android.content.Context
import kotlin.math.roundToInt

fun dpToPx(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}