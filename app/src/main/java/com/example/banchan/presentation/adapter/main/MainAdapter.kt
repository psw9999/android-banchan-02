package com.example.banchan.presentation.adapter.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemListModel
import com.example.banchan.presentation.home.maincook.Filter

enum class Type {
    Grid, Linear
}

class MainAdapter(private val onTypeChanged: (Type) -> Unit, private val onFilterChanged: (Filter) -> Unit) :
    ListAdapter<ItemListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemListModel.Header -> HEADER_VIEW_TYPE
            is ItemListModel.LargeItem -> LARGE_ITEM_VIEW_TYPE
            is ItemListModel.SmallItem -> MEDIUM_ITEM_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                MainFilterViewHolder.create(parent)
            }
            LARGE_ITEM_VIEW_TYPE -> {
                LargeMenuViewHolder.create(parent)
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
            is ItemListModel.Header ->
                (holder as MainFilterViewHolder).bind(item.currentType, onTypeChanged, item.currentFilter, onFilterChanged)
            is ItemListModel.LargeItem -> {
                (holder as LargeMenuViewHolder).bind(item.item) {}
            }
            is ItemListModel.SmallItem -> {
                (holder as MediumMenuViewHolder).bind(item.item) {}
            }
        }
    }

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val LARGE_ITEM_VIEW_TYPE = 1
        const val MEDIUM_ITEM_VIEW_TYPE = 2

        class DiffCallback : DiffUtil.ItemCallback<ItemListModel>() {
            override fun areItemsTheSame(
                oldItem: ItemListModel,
                newItem: ItemListModel
            ): Boolean {
                return (oldItem is ItemListModel.Header && newItem is ItemListModel.Header &&
                        oldItem.currentType == newItem.currentType) ||
                        (oldItem is ItemListModel.SmallItem && newItem is ItemListModel.SmallItem &&
                                oldItem.item.detailHash == newItem.item.detailHash) ||
                        (oldItem is ItemListModel.LargeItem && newItem is ItemListModel.LargeItem &&
                                oldItem.item.detailHash == newItem.item.detailHash)
            }

            override fun areContentsTheSame(
                oldItem: ItemListModel,
                newItem: ItemListModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}