package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasketEmptyAdapter : RecyclerView.Adapter<BasketEmptyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketEmptyViewHolder =
        BasketEmptyViewHolder.create(parent)


    override fun onBindViewHolder(holder: BasketEmptyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1
}