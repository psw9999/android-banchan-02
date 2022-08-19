package com.example.banchan.presentation.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.example.banchan.R
import com.example.banchan.databinding.AmountCounterBinding
import com.example.banchan.util.dimen.dpToPx

class AmountCounter(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: AmountCounterBinding by lazy {
        AmountCounterBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.AmountCounter) {
            setTypeArray(this)
        }
        addView(binding.root)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        setAmount(typedArray.getInteger(R.styleable.AmountCounter_amount, 1))
        setSize(
            typedArray.getDimensionPixelSize(
                R.styleable.AmountCounter_size,
                dpToPx(this.context, 30)
            )
        )
    }

    fun setAmount(amount: Int) {
        if (amount < 1) binding.amount = 1
        else if (amount > 99) binding.amount = 99
        else binding.amount = amount
    }

    fun setSize(size: Int) {
        binding.fabPlus.customSize = size
        binding.fabMinus.customSize = size
    }

    fun setOnMinusClickListener(listener: OnClickListener) {
        binding.fabMinus.setOnClickListener(listener)
    }

    fun setOnPlusClickListener(listener: OnClickListener) {
        binding.fabPlus.setOnClickListener(listener)
    }
}