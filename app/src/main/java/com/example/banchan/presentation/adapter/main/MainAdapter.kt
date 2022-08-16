package com.example.banchan.presentation.adapter.main

import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.adapter.home.HomeEmptyViewHolder
import com.example.banchan.presentation.adapter.home.HomeHeaderViewHolder
import com.example.banchan.presentation.adapter.home.HomeLoadingViewHolder
import com.example.banchan.presentation.home.maincook.Filter

enum class Type {
    Grid, Linear
}

class MainAdapter(
    private val onTypeChanged: (Type) -> Unit,
    private val onFilterChanged: (Filter) -> Unit,
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) :
    ListAdapter<MainItemListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainItemListModel.MainHeader -> HEADER_VIEW_TYPE
            is MainItemListModel.Filter -> FILTER_VIEW_TYPE
            is MainItemListModel.LargeItem -> LARGE_ITEM_VIEW_TYPE
            is MainItemListModel.SmallItem -> MEDIUM_ITEM_VIEW_TYPE
            is MainItemListModel.Loading -> LOADING_VIEW_TYPE
            is MainItemListModel.Error -> ERROR_VIEW_TYPE
            is MainItemListModel.Empty -> EMPTY_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                HomeHeaderViewHolder.create(parent)
            }
            FILTER_VIEW_TYPE -> {
                MainFilterViewHolder.create(parent)
            }
            LARGE_ITEM_VIEW_TYPE -> {
                LargeMenuViewHolder.create(parent)
            }
            MEDIUM_ITEM_VIEW_TYPE -> {
                MediumMenuViewHolder.create(parent)
            }
            LOADING_VIEW_TYPE -> {
                HomeLoadingViewHolder.create(parent)
            }
            ERROR_VIEW_TYPE -> {
                HomeLoadingViewHolder.create(parent)
            }
            EMPTY_VIEW_TYPE -> {
                HomeEmptyViewHolder.create(parent)
            }
            else -> {
                throw UnsupportedOperationException("Unknown view")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MainItemListModel.MainHeader -> {
                (holder as HomeHeaderViewHolder).bind(
                    item.isSubTitleVisible, item.titleStrRes
                )
            }
            is MainItemListModel.Filter ->
                (holder as MainFilterViewHolder).bind(
                    item.currentType,
                    onTypeChanged,
                    item.currentFilter,
                    onFilterChanged
                )
            is MainItemListModel.LargeItem -> {
                (holder as LargeMenuViewHolder).bind(
                    item.item,
                    basketClickListener,
                    productDetailListener
                )
            }
            is MainItemListModel.SmallItem -> {
                (holder as MediumMenuViewHolder).bind(
                    item.item,
                    basketClickListener,
                    productDetailListener
                )
            }
            else -> {

            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val mainListItem = getItem(position)
            if(payloads[0] as Boolean && mainListItem is MainItemListModel.SmallItem) {
                (holder as MediumMenuViewHolder).setItem(mainListItem.item)
            } else if(payloads[0] as Boolean && mainListItem is MainItemListModel.LargeItem) {
                (holder as LargeMenuViewHolder).setItem(mainListItem.item)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val FILTER_VIEW_TYPE = 1
        const val LARGE_ITEM_VIEW_TYPE = 2
        const val MEDIUM_ITEM_VIEW_TYPE = 3
        const val LOADING_VIEW_TYPE = 4
        const val ERROR_VIEW_TYPE = 5
        const val EMPTY_VIEW_TYPE = 6

        class DiffCallback : DiffUtil.ItemCallback<MainItemListModel>() {
            override fun areItemsTheSame(
                oldItem: MainItemListModel,
                newItem: MainItemListModel
            ): Boolean {
                return (oldItem is MainItemListModel.Filter && newItem is MainItemListModel.Filter &&
                        oldItem.currentType == newItem.currentType) ||
                        (oldItem is MainItemListModel.SmallItem && newItem is MainItemListModel.SmallItem &&
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
                return if(oldItem is MainItemListModel.SmallItem && newItem is MainItemListModel.SmallItem) {
                    if (oldItem.item.isCartAdded != newItem.item.isCartAdded) true
                    else null
                } else if(oldItem is MainItemListModel.LargeItem && newItem is MainItemListModel.LargeItem){
                    if (oldItem.item.isCartAdded != newItem.item.isCartAdded) true
                    else null
                } else {
                    null
                }
            }
        }
    }
}

sealed class MainItemListModel {
    data class MainHeader(
        val isSubTitleVisible: Boolean = false,
        @StringRes val titleStrRes: Int = R.string.home_main_cook_title
    ) : MainItemListModel()

    data class Filter(
        val currentType: Type,
        val currentFilter: com.example.banchan.presentation.home.maincook.Filter
    ) : MainItemListModel()

    data class SmallItem(val item: ItemModel) : MainItemListModel()
    data class LargeItem(val item: ItemModel) : MainItemListModel()
    object Loading : MainItemListModel()
    object Error : MainItemListModel()
    object Empty : MainItemListModel()
}