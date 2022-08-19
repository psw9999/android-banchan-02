package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.RecentlyProductItemModel

class BasketRecentlyTabAdapter : RecyclerView.Adapter<BasketRecentlyTabHolder>() {

    private var recentlyViewedList: List<RecentlyProductItemModel> = listOf()

    fun setRecentlyViewedList(recentlyViewedList: List<RecentlyProductItemModel>) {
        this.recentlyViewedList = recentlyViewedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecentlyTabHolder
        = BasketRecentlyTabHolder.create(parent)

    override fun onBindViewHolder(holder: BasketRecentlyTabHolder, position: Int) {
        holder.bind(recentlyViewedList)
    }

    override fun getItemCount(): Int = 1
}