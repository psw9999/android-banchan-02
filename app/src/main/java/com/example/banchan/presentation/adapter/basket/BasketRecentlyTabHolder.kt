package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketRecentlyTabBinding
import com.example.banchan.domain.model.RecentlyProductModel

class BasketRecentlyTabHolder(private val binding: ItemBasketRecentlyTabBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val adapter: BasketRecentlyItemAdapter by lazy { BasketRecentlyItemAdapter() }

    fun bind(recentlyProductList: List<RecentlyProductModel>, onClickRecentlyTab: () -> Unit) {
        initRecyclerView(recentlyProductList)
        binding.tvBasketRecentlyProductPage.setOnClickListener {
            onClickRecentlyTab()
        }
    }

    private fun initRecyclerView(recentlyProductList: List<RecentlyProductModel>) {
        binding.rvBasketRecentlyList.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBasketRecentlyList.adapter = adapter
        adapter.setRecentlyViewedList(recentlyProductList)
    }

    companion object {
        fun create(parent: ViewGroup) = BasketRecentlyTabHolder(
            ItemBasketRecentlyTabBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}