package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketTabBinding

class BasketTabViewHolder(private val binding: ItemBasketTabBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind() {
    }

    companion object {
        fun create(parent: ViewGroup) = BasketTabViewHolder(
            ItemBasketTabBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}