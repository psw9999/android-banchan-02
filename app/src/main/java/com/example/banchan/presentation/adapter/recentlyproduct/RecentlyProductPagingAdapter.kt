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
        }
    }
}