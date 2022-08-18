package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketRecentlyProductBinding
import com.example.banchan.domain.model.RecentlyProductModel

class BasketRecentlyItemHolder(private val binding: ItemBasketRecentlyProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recentlyProductModel: RecentlyProductModel) {
        binding.recentlyProductModel = recentlyProductModel
    }

    companion object {
        fun create(parent: ViewGroup) = BasketRecentlyItemHolder(
            ItemBasketRecentlyProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}