package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasketOrderAdapter: RecyclerView.Adapter<BasketOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketOrderViewHolder
        = BasketOrderViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketOrderViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1
}