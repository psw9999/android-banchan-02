package com.example.banchan.presentation.adapter.menu

import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.databinding.ItemMenuBinding

class MenuViewHolder(private val binding: ItemMenuBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemModel) {
        binding.item = item
    }
}