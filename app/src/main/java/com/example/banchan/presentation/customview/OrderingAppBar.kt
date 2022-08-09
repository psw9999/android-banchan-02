package com.example.banchan.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.example.banchan.R
import com.example.banchan.databinding.AppbarOrderingBinding

class OrderingAppBar(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: AppbarOrderingBinding by lazy {
        AppbarOrderingBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.OrderingAppBar) {
            setTypeArray(this)
        }
        addView(binding.root)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        setCartCount(typedArray.getInteger(R.styleable.OrderingAppBar_cartCount, 0))
        setIsShipping(typedArray.getBoolean(R.styleable.OrderingAppBar_isShipping, false))
    }

    fun setCartCount(cartCount: Int) {
        binding.cartCount = cartCount
    }

    fun setIsShipping(isShipping: Boolean) {
        binding.isShipping = isShipping
    }

    fun setOnCartClickListener(listener: OnClickListener) {
        binding.ivAbCart.setOnClickListener(listener)
        binding.ivAbCartAction.setOnClickListener(listener)
    }

    fun setOnProfileClickListener(listener: OnClickListener) {
        binding.ivAbProfile.setOnClickListener(listener)
    }

}