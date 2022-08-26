package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.UiState

class BasketRecentlyTabAdapter(
    private val onClickRecentlyTab: () -> Unit,
    private val onItemClick: (ItemModel) -> Unit,
    private val onRefreshBtnClick: () -> Unit
) : RecyclerView.Adapter<BasketRecentlyTabHolder>() {

    private var recentlyViewedList: UiState<List<ItemModel>> = UiState.Loading

    fun setRecentlyViewedList(recentlyViewedList: UiState<List<ItemModel>>) {
        this.recentlyViewedList = recentlyViewedList
        notifyItemChanged(0, true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecentlyTabHolder =
        BasketRecentlyTabHolder.create(parent)

    override fun onBindViewHolder(holder: BasketRecentlyTabHolder, position: Int) {
        holder.initBind(recentlyViewedList, onClickRecentlyTab, onItemClick, onRefreshBtnClick)
    }

    override fun onBindViewHolder(
        holder: BasketRecentlyTabHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.refreshBind(recentlyViewedList)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun getItemCount(): Int = 1
}