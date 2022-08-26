package com.example.banchan.presentation.adapter.ordersuccess

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.data.source.local.history.HistoryItem
import com.example.banchan.databinding.ItemOrderSuccessBinding

class OrderSuccessItemViewHolder(
    private val binding: ItemOrderSuccessBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(historyItem: HistoryItem) {
        binding.item = historyItem
    }

    companion object {
        fun create(parent: ViewGroup) = OrderSuccessItemViewHolder(
            ItemOrderSuccessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}