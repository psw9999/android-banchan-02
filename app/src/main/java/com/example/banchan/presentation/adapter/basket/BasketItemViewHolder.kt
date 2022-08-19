package com.example.banchan.presentation.adapter.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBasketBinding
import com.example.banchan.domain.model.BasketModel

class BasketItemViewHolder(private val binding: ItemBasketBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initBind(
        basketModel: BasketModel,
        onCheckBoxClick: ((BasketModel) -> Unit),
        onClickDeleteBtn: ((BasketModel) -> Unit)
    ) {
        binding.basketModel = basketModel
        binding.cbBasketItem.setOnClickListener { onCheckBoxClick(basketModel) }
        binding.ivBasketItemDelete.setOnClickListener { onClickDeleteBtn(basketModel) }
    }

    fun refreshBind(
        basketModel: BasketModel,
        onCheckBoxClick: ((BasketModel) -> Unit),
        onClickDeleteBtn: ((BasketModel) -> Unit)
    ) {
        binding.basketModel = basketModel
        binding.cbBasketItem.setOnClickListener { onCheckBoxClick(basketModel) }
        binding.ivBasketItemDelete.setOnClickListener { onClickDeleteBtn(basketModel) }
        binding.executePendingBindings()
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