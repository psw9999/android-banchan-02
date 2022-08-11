package com.example.banchan.presentation.adapter.home

import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemHomeHeaderBinding

class HomeHeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(isSubTitleVisible: Boolean, title: String) {
        binding.title = title
        binding.isSubtitleVisible = isSubTitleVisible
    }
}