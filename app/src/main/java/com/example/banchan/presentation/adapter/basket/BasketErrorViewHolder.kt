package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketErrorBinding

class BasketErrorViewHolder(private val binding: ItemBasketErrorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initBind(onClickRefresh: (() -> Unit)) {
        binding.btnHomeErrorReload.setOnClickListener { onClickRefresh() }
    }

    companion object {
        fun create(parent: ViewGroup) = BasketErrorViewHolder(
            ItemBasketErrorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}