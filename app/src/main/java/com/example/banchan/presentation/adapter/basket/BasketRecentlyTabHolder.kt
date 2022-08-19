package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketRecentlyTabBinding
import com.example.banchan.domain.model.RecentlyProductModel

class BasketRecentlyTabHolder(private val binding: ItemBasketRecentlyTabBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initBind(recentlyProductList: List<RecentlyProductModel>, onClickRecentlyTab: () -> Unit) {
        initRecyclerView(recentlyProductList)
        binding.tvBasketRecentlyProductPage.setOnClickListener {
            onClickRecentlyTab()
        }
    }

    fun refreshBind(recentlyProductList: List<RecentlyProductModel>) {
        val recentlyProductListAdapter = binding.rvBasketRecentlyList.adapter as BasketRecentlyListAdapter
        recentlyProductListAdapter.submitList(recentlyProductList)
    }

    private fun initRecyclerView(recentlyProductList: List<RecentlyProductModel>) {
        val recentlyProductListAdapter = BasketRecentlyListAdapter()
        binding.rvBasketRecentlyList.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBasketRecentlyList.adapter = recentlyProductListAdapter
        recentlyProductListAdapter.submitList(recentlyProductList)
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