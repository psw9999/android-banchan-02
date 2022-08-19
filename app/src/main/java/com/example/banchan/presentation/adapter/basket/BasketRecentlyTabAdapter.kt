package com.example.banchan.presentation.adapter.basket

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.RecentlyProductModel

class BasketRecentlyTabAdapter(private val onClickRecentlyTab: () -> Unit) :
    RecyclerView.Adapter<BasketRecentlyTabHolder>() {

    private var recentlyViewedList: List<RecentlyProductModel> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setRecentlyViewedList(recentlyViewedList: List<RecentlyProductModel>) {
        this.recentlyViewedList = recentlyViewedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecentlyTabHolder =
        BasketRecentlyTabHolder.create(parent)

    override fun onBindViewHolder(holder: BasketRecentlyTabHolder, position: Int) {
        holder.bind(recentlyViewedList, onClickRecentlyTab)
    }

    override fun getItemCount(): Int = 1
}