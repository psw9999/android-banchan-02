package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasketTabAdapter(
    private val onClickCheckBoxTab: ((Int)->Unit),
    private val onClickSeletedDeleteBtn: (()->Unit)
) : RecyclerView.Adapter<BasketTabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketTabViewHolder
        = BasketTabViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketTabViewHolder, position: Int) {
        holder.bind(onClickCheckBoxTab, onClickSeletedDeleteBtn)
    }

    override fun getItemCount(): Int = 1
}