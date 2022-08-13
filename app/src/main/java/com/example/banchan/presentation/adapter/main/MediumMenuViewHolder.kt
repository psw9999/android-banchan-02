package com.example.banchan.presentation.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemMenuMediumBinding
import com.example.banchan.domain.model.ItemModel

class MediumMenuViewHolder(private val binding: ItemMenuMediumBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemModel, cartClick: (ItemModel) -> Unit) {
        binding.item = item
        binding.ivCart.setOnClickListener { cartClick(item) }
        binding.executePendingBindings()
    }

    fun setItem(item: ItemModel) {
        binding.item = item
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) = MediumMenuViewHolder(
            ItemMenuMediumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}