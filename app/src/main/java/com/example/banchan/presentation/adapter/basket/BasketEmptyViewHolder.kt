package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketEmptyBinding

class BasketEmptyViewHolder(binding: ItemBasketEmptyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = BasketEmptyViewHolder(
            ItemBasketEmptyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}