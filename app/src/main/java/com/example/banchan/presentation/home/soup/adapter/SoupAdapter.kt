package com.example.banchan.presentation.home.soup.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.home.maincook.Filter
import com.example.banchan.presentation.home.maincook.adapter.MediumMenuViewHolder

class SoupAdapter(private val onFilterChanged: (Filter) -> Unit) :
    ListAdapter<SoupItemListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SoupItemListModel.Header -> HEADER_VIEW_TYPE
            is SoupItemListModel.SmallItem -> MEDIUM_ITEM_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                TotalFilterViewViewHolder.create(parent)
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
            is SoupItemListModel.Header ->
                (holder as TotalFilterViewViewHolder).bind(
                    item.currentFilter,
                    item.total,
                    onFilterChanged
                )
            is SoupItemListModel.SmallItem -> {
                (holder as MediumMenuViewHolder).bind(item.item) {}
            }
        }
    }

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val MEDIUM_ITEM_VIEW_TYPE = 1

        class DiffCallback : DiffUtil.ItemCallback<SoupItemListModel>() {
            override fun areItemsTheSame(
                oldItem: SoupItemListModel,
                newItem: SoupItemListModel
            ): Boolean {
                return (oldItem is SoupItemListModel.Header && newItem is SoupItemListModel.Header &&
                        oldItem.total == newItem.total) ||
                        (oldItem is SoupItemListModel.SmallItem && newItem is SoupItemListModel.SmallItem &&
                                oldItem.item.detailHash == newItem.item.detailHash)
            }

            override fun areContentsTheSame(
                oldItem: SoupItemListModel,
                newItem: SoupItemListModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed class SoupItemListModel {
    data class Header(val total: Int, val currentFilter: Filter) : SoupItemListModel()
    data class SmallItem(val item: ItemModel) : SoupItemListModel()
}