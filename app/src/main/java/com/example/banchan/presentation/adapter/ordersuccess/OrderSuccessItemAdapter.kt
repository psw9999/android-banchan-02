package com.example.banchan.presentation.adapter.ordersuccess

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.presentation.ordersuccess.OrderSuccessListModel

class OrderSuccessItemAdapter :
    ListAdapter<OrderSuccessListModel.Item, OrderSuccessItemViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSuccessItemViewHolder =
        OrderSuccessItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: OrderSuccessItemViewHolder, position: Int) {
        holder.bind(getItem(position).historyItem)
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<OrderSuccessListModel.Item>() {
            override fun areItemsTheSame(
                oldItem: OrderSuccessListModel.Item,
                newItem: OrderSuccessListModel.Item
            ): Boolean {
                return oldItem.historyItem.itemId == newItem.historyItem.itemId
            }

            override fun areContentsTheSame(
                oldItem: OrderSuccessListModel.Item,
                newItem: OrderSuccessListModel.Item
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}