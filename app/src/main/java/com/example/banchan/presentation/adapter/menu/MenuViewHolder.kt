package com.example.banchan.presentation.adapter.menu

import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.databinding.ItemMenuBinding

class MenuViewHolder(private val binding: ItemMenuBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemModel, basketClickListener: (ItemModel) -> Unit) {
        binding.item = item
        binding.ivMenuBasket.setOnClickListener{ basketClickListener.invoke(item) }
    }

    fun setItem(item: ItemModel) {
        binding.item = item
        binding.executePendingBindings()
    }
}