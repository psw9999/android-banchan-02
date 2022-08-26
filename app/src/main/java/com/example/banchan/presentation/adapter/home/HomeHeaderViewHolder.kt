package com.example.banchan.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemHomeHeaderBinding

class HomeHeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(isSubTitleVisible: Boolean, titleStrRes: Int) {
        binding.title = binding.root.resources.getString(titleStrRes)
        binding.isSubtitleVisible = isSubTitleVisible
    }

    companion object {
        fun create(parent: ViewGroup) = HomeHeaderViewHolder(
            ItemHomeHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}