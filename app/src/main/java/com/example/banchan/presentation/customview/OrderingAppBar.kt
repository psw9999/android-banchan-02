package com.example.banchan.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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
        getAttrs(attrs)
        addView(binding.root)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OrderingAppBar)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        binding.cartCount = typedArray.getInteger(R.styleable.OrderingAppBar_cartCount, 0)
        binding.isShipping = typedArray.getBoolean(R.styleable.OrderingAppBar_isShipping, false)
    }

    fun setOnCartClickListener(listener: OnClickListener) {
        binding.ivAbCart.setOnClickListener(listener)
        binding.ivAbCartAction.setOnClickListener(listener)
    }

    fun setOnProfileClickListener(listener: OnClickListener) {
        binding.ivAbProfile.setOnClickListener(listener)
    }

}