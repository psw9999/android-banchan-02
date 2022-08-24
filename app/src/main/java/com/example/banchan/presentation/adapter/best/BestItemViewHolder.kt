package com.example.banchan.presentation.adapter.best

import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemMenuBinding
import com.example.banchan.domain.model.ItemModel

class BestItemViewHolder(private val binding: ItemMenuBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: ItemModel,
        basketClickListener: (ItemModel) -> Unit,
        productDetailListener: (ItemModel) -> Unit
    ) {
        binding.item = item
        binding.ivMenuBasket.setOnClickListener{ basketClickListener.invoke(item) }
        binding.root.setOnClickListener { productDetailListener.invoke(item) }
        binding.executePendingBindings()
    }
}