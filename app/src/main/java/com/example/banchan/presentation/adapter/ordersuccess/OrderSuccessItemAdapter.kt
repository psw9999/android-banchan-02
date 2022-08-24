package com.example.banchan.presentation.adapter.ordersuccess

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.data.source.local.history.HistoryItem

class OrderSuccessItemAdapter :
    ListAdapter<HistoryItem, OrderSuccessItemViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSuccessItemViewHolder =
        OrderSuccessItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: OrderSuccessItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}