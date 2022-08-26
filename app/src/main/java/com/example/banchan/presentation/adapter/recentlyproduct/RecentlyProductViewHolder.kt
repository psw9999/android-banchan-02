package com.example.banchan.presentation.adapter.recentlyproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemRecentBinding
import com.example.banchan.domain.model.ItemModel

class RecentlyProductViewHolder(
    private val binding: ItemRecentBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: ItemModel,
        cartClick: (ItemModel) -> Unit,
        productClick: (ItemModel) -> Unit
    ) {
        binding.item = item
        binding.root.setOnClickListener { productClick(item) }
        binding.ivMenuBasket.setOnClickListener { cartClick(item) }
    }

    companion object {
        fun create(parent: ViewGroup) = RecentlyProductViewHolder(
            ItemRecentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}