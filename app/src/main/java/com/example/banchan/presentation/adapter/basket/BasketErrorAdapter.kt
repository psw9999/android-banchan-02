package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasketErrorAdapter(
    private val onClickRefresh: (() -> Unit)
) : RecyclerView.Adapter<BasketErrorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketErrorViewHolder =
        BasketErrorViewHolder.create(parent)


    override fun onBindViewHolder(holder: BasketErrorViewHolder, position: Int) {
        holder.initBind(onClickRefresh)
    }

    override fun getItemCount(): Int = 1
}