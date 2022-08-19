package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.RecentlyProductItemModel

class BasketRecentlyItemAdapter(

) : RecyclerView.Adapter<BasketRecentlyItemHolder>() {

    private var recentlyViewedList: List<RecentlyProductItemModel> = listOf()

    fun setRecentlyViewedList(recentlyViewedList: List<RecentlyProductItemModel>) {
        this.recentlyViewedList = recentlyViewedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecentlyItemHolder =
        BasketRecentlyItemHolder.create(parent)

    override fun onBindViewHolder(holder: BasketRecentlyItemHolder, position: Int) {
        holder.bind(recentlyViewedList[position])
    }

    override fun getItemCount(): Int = recentlyViewedList.size
}