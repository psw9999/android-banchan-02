package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.ItemModel

class BasketRecentlyListAdapter(private val onItemClick: (ItemModel) -> Unit) : ListAdapter<ItemModel, BasketRecentlyItemHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(
                oldItem: ItemModel,
                newItem: ItemModel
            ): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(
                oldItem: ItemModel,
                newItem: ItemModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: ItemModel, newItem: ItemModel): Any? {
                return oldItem.time != newItem.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecentlyItemHolder =
        BasketRecentlyItemHolder.create(parent)

    override fun onBindViewHolder(holder: BasketRecentlyItemHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    override fun onBindViewHolder(
        holder: BasketRecentlyItemHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.bind(getItem(position), onItemClick)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }
}