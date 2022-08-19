package com.example.banchan.presentation.adapter.recentlyproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.RecentlyProductModel

class RecentlyProductAdapter(
    private val cartClick: (RecentlyProductModel) -> Unit,
    private val productClick: (RecentlyProductModel) -> Unit
) :
    ListAdapter<RecentlyProductModel, RecentlyProductViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyProductViewHolder =
        RecentlyProductViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecentlyProductViewHolder, position: Int) {
        holder.bind(getItem(position), cartClick, productClick)
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<RecentlyProductModel>() {
            override fun areItemsTheSame(
                oldItem: RecentlyProductModel,
                newItem: RecentlyProductModel
            ): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(
                oldItem: RecentlyProductModel,
                newItem: RecentlyProductModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}