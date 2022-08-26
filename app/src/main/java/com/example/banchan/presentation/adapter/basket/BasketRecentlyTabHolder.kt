package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketRecentlyTabBinding
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.UiState

class BasketRecentlyTabHolder(private val binding: ItemBasketRecentlyTabBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initBind(
        recentlyProductList: UiState<List<ItemModel>>,
        onClickRecentlyTab: () -> Unit,
        onItemClick: (ItemModel) -> Unit,
        onRefreshBtnClick: () -> Unit
    ) {
        binding.uiState = recentlyProductList
        val list = if(recentlyProductList is UiState.Success) recentlyProductList.item.take(7) else listOf()
        initRecyclerView(list, onItemClick)
        binding.tvBasketRecentlyProductPage.setOnClickListener {
            onClickRecentlyTab()
        }
        binding.layoutErrorRecentProduct.btnHomeErrorReload.setOnClickListener {
            onRefreshBtnClick()
        }
    }

    fun refreshBind(recentlyProductList: UiState<List<ItemModel>>) {
        binding.uiState = recentlyProductList
        if (recentlyProductList is UiState.Success) {
            val recentlyProductListAdapter = binding.rvBasketRecentlyList.adapter as BasketRecentlyListAdapter
            recentlyProductListAdapter.submitList(recentlyProductList.item.take(7))
        }
        binding.executePendingBindings()
    }

    private fun initRecyclerView(
        recentlyProductList: List<ItemModel>,
        onItemClick: (ItemModel) -> Unit
    ) {
        val recentlyProductListAdapter = BasketRecentlyListAdapter(onItemClick)
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