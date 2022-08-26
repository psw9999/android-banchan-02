package com.example.banchan.presentation.adapter.recentlyproduct

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.recentlyproduct.RecentUiModel

class RecentlyProductPagingAdapter(
    private val cartClick: (ItemModel) -> Unit,
    private val productClick: (ItemModel) -> Unit
) :
    PagingDataAdapter<RecentUiModel, RecentlyProductViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyProductViewHolder =
        RecentlyProductViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecentlyProductViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            if (it is RecentUiModel.Success) holder.bind(it.itemModel, cartClick, productClick)
        }
    }

    override fun onBindViewHolder(
        holder: RecentlyProductViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                val item = getItem(position) as RecentUiModel.Success
                holder.bind(item.itemModel, cartClick, productClick)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<RecentUiModel>() {
            override fun areItemsTheSame(
                oldItem: RecentUiModel,
                newItem: RecentUiModel
            ): Boolean {
                return oldItem is RecentUiModel.Success && newItem is RecentUiModel.Success && oldItem.itemModel.detailHash == newItem.itemModel.detailHash
            }

            override fun areContentsTheSame(
                oldItem: RecentUiModel,
                newItem: RecentUiModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: RecentUiModel, newItem: RecentUiModel): Any? {
                if (oldItem is RecentUiModel.Success && newItem is RecentUiModel.Success) {
                    return (oldItem.itemModel.time != newItem.itemModel.time) || (oldItem.itemModel.isCartAdded != newItem.itemModel.isCartAdded)
                }
                return null
            }
        }
    }
}