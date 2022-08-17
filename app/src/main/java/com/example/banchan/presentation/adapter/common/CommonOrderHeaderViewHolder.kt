package com.example.banchan.presentation.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemOrderHeaderBinding
import com.example.banchan.presentation.ordersuccess.OrderCommonListModel

class CommonOrderHeaderViewHolder(private val binding: ItemOrderHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(header: OrderCommonListModel.Header) {
        binding.item = header
    }

    companion object {
        fun create(parent: ViewGroup) = CommonOrderHeaderViewHolder(
            ItemOrderHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}