package com.example.banchan.presentation.adapter.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemDetailOrderBinding
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.util.ext.onMinusClick
import com.example.banchan.util.ext.onPlusClick

class ProductInfoViewHolder(
    private val binding: ItemDetailOrderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        productInfo: ProductDetailModel?,
        amount: Int,
        onMinusClick: () -> Unit,
        onPlusClick: () -> Unit,
        onBasketAddClick: () -> Unit,
    ) {
        binding.productDetail = productInfo
        binding.amount = amount
        binding.counterDetailAmount.onMinusClick{ onMinusClick() }
        binding.counterDetailAmount.onPlusClick{ onPlusClick() }
        binding.btnDetailProductOrder.setOnClickListener { onBasketAddClick() }
    }

    fun bindAmount(
        amount: Int
    ) {
        binding.amount = amount
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) = ProductInfoViewHolder(
            ItemDetailOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}