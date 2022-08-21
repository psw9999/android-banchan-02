package com.example.banchan.presentation.adapter.orderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemOrderListBinding
import com.example.banchan.domain.model.OrderListModel

class OrderListViewHolder(private val binding: ItemOrderListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(orderListModel: OrderListModel, onOrderItemClickListener: (()->Unit)) {
        binding.orderListModel = orderListModel
        binding.root.setOnClickListener { onOrderItemClickListener() }
    }

    companion object {
        fun create(parent: ViewGroup) = OrderListViewHolder(
            ItemOrderListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}