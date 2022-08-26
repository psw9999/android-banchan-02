package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasketLoadingAdapter : RecyclerView.Adapter<BasketLoadingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketLoadingViewHolder =
        BasketLoadingViewHolder.create(parent)


    override fun onBindViewHolder(holder: BasketLoadingViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1
}