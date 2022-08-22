package com.example.banchan.presentation.adapter.recentlyproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.ItemModel

class RecentlyProductAdapter(
    private val cartClick: (ItemModel) -> Unit,
    private val productClick: (ItemModel) -> Unit
) :
    ListAdapter<ItemModel, RecentlyProductViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyProductViewHolder =
        RecentlyProductViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecentlyProductViewHolder, position: Int) {
        holder.bind(getItem(position), cartClick, productClick)
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<ItemModel>() {
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
        }
    }
}