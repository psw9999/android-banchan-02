package com.example.banchan.presentation.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.ItemOrderFooterBinding
import com.example.banchan.presentation.ordersuccess.OrderSuccessListModel

class CommonOrderFooterViewHolder(private val binding: ItemOrderFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(footer: OrderSuccessListModel.Footer) {
        val res = binding.root.resources
        binding.tvOrderPrice.text = res.getString(R.string.amount_format, footer.orderPrice)
        binding.tvDeliveryFee.text = res.getString(R.string.amount_format, footer.deliveryFee)
        binding.tvItemTotal.text = res.getString(R.string.amount_format, footer.totalPrice)
    }

    companion object {
        fun create(parent: ViewGroup) = CommonOrderFooterViewHolder(
            ItemOrderFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}