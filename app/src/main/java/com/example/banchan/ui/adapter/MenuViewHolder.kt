package com.example.banchan.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.data.response.ItemModel
import com.example.banchan.databinding.ItemMenuBinding

class MenuViewHolder(private val binding: ItemMenuBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemModel) {
        binding.item = item
    }
}