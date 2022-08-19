package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.RecentlyProductModel

class BasketRecentlyListAdapter(
) : ListAdapter<RecentlyProductModel, BasketRecentlyItemHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecentlyProductModel>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecentlyItemHolder =
        BasketRecentlyItemHolder.create(parent)

    override fun onBindViewHolder(holder: BasketRecentlyItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}