package com.example.banchan.presentation.adapter.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.home.maincook.Filter

enum class Type {
    Grid, Linear
}

class MainItemAdapter(
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) :
    ListAdapter<MainItemListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainItemListModel.MediumItem -> MEDIUM_ITEM_VIEW_TYPE
            is MainItemListModel.LargeItem -> LARGE_ITEM_VIEW_TYPE
            else -> {
                throw UnsupportedOperationException("Unknown view")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIUM_ITEM_VIEW_TYPE -> {
                MediumMenuViewHolder.create(parent)
            }
            LARGE_ITEM_VIEW_TYPE -> {
                LargeMenuViewHolder.create(parent)
            }
            else -> {
                throw UnsupportedOperationException("Unknown view")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MainItemListModel.MediumItem -> {
                (holder as MediumMenuViewHolder).bind(
                    item.item,
                    basketClickListener,
                    productDetailListener
                )
            }
            is MainItemListModel.LargeItem -> {
                (holder as LargeMenuViewHolder).bind(
                    item.item,
                    basketClickListener,
                    productDetailListener
                )
            }
            else -> {}
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val mainListItem = getItem(position)
            if (payloads[0] as Boolean && mainListItem is MainItemListModel.MediumItem) {
                (holder as MediumMenuViewHolder).setItem(mainListItem.item)
            } else if (payloads[0] as Boolean && mainListItem is MainItemListModel.LargeItem) {
                (holder as LargeMenuViewHolder).setItem(mainListItem.item)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    companion object {
        const val MEDIUM_ITEM_VIEW_TYPE = 0
        const val LARGE_ITEM_VIEW_TYPE = 1

        class DiffCallback : DiffUtil.ItemCallback<MainItemListModel>() {
            override fun areItemsTheSame(
                oldItem: MainItemListModel,
                newItem: MainItemListModel
            ): Boolean {
                return (oldItem is MainItemListModel.MediumItem && newItem is MainItemListModel.MediumItem &&
                        oldItem.item.detailHash == newItem.item.detailHash) ||
                        (oldItem is MainItemListModel.LargeItem && newItem is MainItemListModel.LargeItem &&
                                oldItem.item.detailHash == newItem.item.detailHash)
            }

            override fun areContentsTheSame(
                oldItem: MainItemListModel,
                newItem: MainItemListModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(
                oldItem: MainItemListModel,
                newItem: MainItemListModel
            ): Any? {
                return if (oldItem is MainItemListModel.MediumItem && newItem is MainItemListModel.MediumItem) {
                    if (oldItem.item.isCartAdded != newItem.item.isCartAdded) true
                    else null
                } else if (oldItem is MainItemListModel.LargeItem && newItem is MainItemListModel.LargeItem) {
                    if (oldItem.item.isCartAdded != newItem.item.isCartAdded) true
                    else null
                } else {
                    null
                }
            }
        }
    }
}

sealed interface MainItemListModel {
    data class MediumItem(val item: ItemModel) : MainItemListModel
    data class LargeItem(val item: ItemModel) : MainItemListModel
}