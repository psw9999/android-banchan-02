package com.example.banchan.presentation.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemMenuLargeBinding
import com.example.banchan.domain.model.ItemModel

class LargeMenuViewHolder(private val binding: ItemMenuLargeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemModel, onClick: (String) -> Unit) {
        binding.item = item
        binding.ivCart.setOnClickListener { onClick(item.detailHash) }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) = LargeMenuViewHolder(
            ItemMenuLargeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}