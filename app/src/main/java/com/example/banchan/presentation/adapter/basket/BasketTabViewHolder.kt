package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketTabBinding
import com.example.banchan.util.ext.toInt

class BasketTabViewHolder(
    private val binding: ItemBasketTabBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        onClickCheckBoxTab: ((Int) -> Unit),
        onClickSelectedDeleteBtn: (() -> Unit),
        isAllBasketItemSelected: Boolean
    ) {
        binding.isAllBasketItemSelected = isAllBasketItemSelected
        binding.ivBasketDoAllCheck.setOnClickListener { onClickCheckBoxTab((!isAllBasketItemSelected).toInt()) }
        binding.tvBasketCheckDelete.setOnClickListener { onClickSelectedDeleteBtn() }
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