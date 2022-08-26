package com.example.banchan.util.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.banchan.presentation.custom.AmountCounter

@BindingAdapter("app:amount")
fun AmountCounter.amount(amount: Int) {
    this.setAmount(amount)
}

@BindingAdapter("app:onMinusClick")
fun AmountCounter.onMinusClick(listener: View.OnClickListener) {
    this.setOnMinusClickListener(listener)
}

@BindingAdapter("app:onPlusClick")
fun AmountCounter.onPlusClick(listener: View.OnClickListener) {
    this.setOnPlusClickListener(listener)
}