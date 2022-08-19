package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketOrderBinding
import com.example.banchan.domain.model.OrderModel

class BasketOrderViewHolder(private val binding: ItemBasketOrderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(orderModel: OrderModel) {
        binding.orderModel = orderModel
    }

    companion object {
        fun create(parent: ViewGroup) = BasketOrderViewHolder(
            ItemBasketOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}