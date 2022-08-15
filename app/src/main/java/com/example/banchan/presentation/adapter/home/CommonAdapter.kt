package com.example.banchan.presentation.adapter.home

import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.adapter.main.MediumMenuViewHolder
import com.example.banchan.presentation.home.maincook.Filter

class CommonAdapter(
    private val onFilterChanged: (Filter) -> Unit,
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) :
    ListAdapter<CommonItemListModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommonItemListModel.CommonHeader -> HEADER_VIEW_TYPE
            is CommonItemListModel.Filter -> FILTER_VIEW_TYPE
            is CommonItemListModel.SmallItem -> MEDIUM_ITEM_VIEW_TYPE
            is CommonItemListModel.Loading -> LOADING_VIEW_TYPE
            is CommonItemListModel.Error -> ERROR_VIEW_TYPE
            is CommonItemListModel.Empty -> EMPTY_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                HomeHeaderViewHolder.create(parent)
            }
            FILTER_VIEW_TYPE -> {
                CommonFilterViewViewHolder.create(parent)
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
            is CommonItemListModel.CommonHeader -> {
                (holder as HomeHeaderViewHolder).bind(titleStrRes = item.titleStrRes, isSubTitleVisible = false)
            }
            is CommonItemListModel.Filter ->
                (holder as CommonFilterViewViewHolder).bind(
                    item.currentFilter,
                    item.total,
                    onFilterChanged
                )
            is CommonItemListModel.SmallItem -> {
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
            val commonListItem = getItem(position)
            if (payloads[0] as Boolean && commonListItem is CommonItemListModel.SmallItem) {
                (holder as MediumMenuViewHolder).setItem(commonListItem.item)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val FILTER_VIEW_TYPE = 1
        const val MEDIUM_ITEM_VIEW_TYPE = 2
        const val LOADING_VIEW_TYPE = 3
        const val ERROR_VIEW_TYPE = 4
        const val EMPTY_VIEW_TYPE = 5

        class DiffCallback : DiffUtil.ItemCallback<CommonItemListModel>() {
            override fun areItemsTheSame(
                oldItem: CommonItemListModel,
                newItem: CommonItemListModel
            ): Boolean {
                return (oldItem is CommonItemListModel.Filter && newItem is CommonItemListModel.Filter &&
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

            override fun getChangePayload(
                oldItem: CommonItemListModel,
                newItem: CommonItemListModel
            ): Any? {
                return if (oldItem is CommonItemListModel.SmallItem && newItem is CommonItemListModel.SmallItem) {
                    if (oldItem.item.isCartAdded != newItem.item.isCartAdded) true
                    else null
                } else {
                    null
                }
            }
        }
    }
}

sealed class CommonItemListModel {
    data class CommonHeader(
        val isSubTitleVisible: Boolean = false,
        @StringRes val titleStrRes: Int
    ) : CommonItemListModel()

    data class Filter(
        val total: Int,
        val currentFilter: com.example.banchan.presentation.home.maincook.Filter
    ) : CommonItemListModel()

    data class SmallItem(val item: ItemModel) : CommonItemListModel()

    object Loading : CommonItemListModel()
    object Error : CommonItemListModel()
    object Empty : CommonItemListModel()
}