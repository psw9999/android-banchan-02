package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketBinding
import com.example.banchan.domain.model.BasketModel

class BasketItemViewHolder(private val binding: ItemBasketBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun binding(basketModel: BasketModel) {
        binding.basketModel = basketModel
    }

    companion object {
        fun create(parent: ViewGroup) = BasketItemViewHolder(
            ItemBasketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}