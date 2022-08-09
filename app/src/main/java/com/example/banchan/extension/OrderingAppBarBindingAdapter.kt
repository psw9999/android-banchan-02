package com.example.banchan.extension

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.banchan.presentation.customview.OrderingAppBar

@BindingAdapter("app:cartCount")
fun OrderingAppBar.cartCount(cartCount: Int) {
    this.setCartCount(cartCount)
}

@BindingAdapter("app:isShipping")
fun OrderingAppBar.isShipping(isShipping: Boolean) {
    this.setIsShipping(isShipping)
}

@BindingAdapter("app:onCartClick")
fun OrderingAppBar.onCartClick(listener: View.OnClickListener) {
    this.setOnCartClickListener(listener)
}

@BindingAdapter("app:onProfileClick")
fun OrderingAppBar.onProfileClick(listener: View.OnClickListener) {
    this.setOnProfileClickListener(listener)
}
