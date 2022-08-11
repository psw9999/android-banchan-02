package com.example.banchan.presentation.adapter.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.home.maincook.Filter
import com.example.banchan.presentation.adapter.main.MediumMenuViewHolder

class CommonAdapter(private val onFilterChanged: (Filter) -> Unit) :
    ListAdapter<CommonItemListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommonItemListModel.Header -> HEADER_VIEW_TYPE
            is CommonItemListModel.SmallItem -> MEDIUM_ITEM_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                CommonFilterViewViewHolder.create(parent)
            }
            MEDIUM_ITEM_VIEW_TYPE -> {
                MediumMenuViewHolder.create(parent)
            }
            else -> {
                throw UnsupportedOperationException("Unknown view")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CommonItemListModel.Header ->
                (holder as CommonFilterViewViewHolder).bind(
                    item.currentFilter,
                    item.total,
                    onFilterChanged
                )
            is CommonItemListModel.SmallItem -> {
                (holder as MediumMenuViewHolder).bind(item.item) {}
            }
        }
    }

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val MEDIUM_ITEM_VIEW_TYPE = 1

        class DiffCallback : DiffUtil.ItemCallback<CommonItemListModel>() {
            override fun areItemsTheSame(
                oldItem: CommonItemListModel,
                newItem: CommonItemListModel
            ): Boolean {
                return (oldItem is CommonItemListModel.Header && newItem is CommonItemListModel.Header &&
                        oldItem.total == newItem.total) ||
                        (oldItem is CommonItemListModel.SmallItem && newItem is CommonItemListModel.SmallItem &&
                                oldItem.item.detailHash == newItem.item.detailHash)
            }

            override fun areContentsTheSame(
                oldItem: CommonItemListModel,
                newItem: CommonItemListModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed class CommonItemListModel {
    data class Header(val total: Int, val currentFilter: Filter) : CommonItemListModel()
    data class SmallItem(val item: ItemModel) : CommonItemListModel()
}