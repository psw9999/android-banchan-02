package com.example.banchan.presentation.adapter.orderlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.OrderListModel

class OrderListAdapter(
    private val onOrderClickListener: (() -> Unit)
): ListAdapter<OrderListModel, OrderListViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OrderListModel>() {
            override fun areItemsTheSame(oldItem: OrderListModel, newItem: OrderListModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: OrderListModel, newItem: OrderListModel): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: OrderListModel, newItem: OrderListModel): Any? {
                return (oldItem.isCompleted != newItem.isCompleted)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder =
        OrderListViewHolder.create(parent)


    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(getItem(position), onOrderClickListener)
    }

    override fun onBindViewHolder(
        holder: OrderListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.bind(getItem(position), onOrderClickListener)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }
}