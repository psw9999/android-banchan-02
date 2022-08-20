package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
class BasketTabAdapter(
    private val onClickCheckBoxTab: ((Int) -> Unit),
    private val onClickSelectedDeleteBtn: (() -> Unit)
) : RecyclerView.Adapter<BasketTabViewHolder>() {

    private var isAllBasketItemSelected = true

    fun setIsAllBasketItemSelected(isAllBasketItemSelected: Boolean) {
        this.isAllBasketItemSelected = isAllBasketItemSelected
        notifyItemChanged(0, true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketTabViewHolder =
        BasketTabViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketTabViewHolder, position: Int) {
        holder.bind(onClickCheckBoxTab, onClickSelectedDeleteBtn, isAllBasketItemSelected)
    }

    override fun onBindViewHolder(
        holder: BasketTabViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.bind(onClickCheckBoxTab, onClickSelectedDeleteBtn, isAllBasketItemSelected)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun getItemCount(): Int = 1
}