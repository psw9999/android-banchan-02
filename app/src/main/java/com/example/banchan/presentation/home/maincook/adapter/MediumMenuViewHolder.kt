package com.example.banchan.presentation.home.maincook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemMenuMediumBinding
import com.example.banchan.domain.model.ItemModel

class MediumMenuViewHolder(private val binding: ItemMenuMediumBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemModel, onClick: (String) -> Unit) {
        binding.item = item
        binding.ivCart.setOnClickListener { onClick(item.detailHash) }
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