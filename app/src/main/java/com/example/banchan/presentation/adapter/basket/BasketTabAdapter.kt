package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasketTabAdapter(

) : RecyclerView.Adapter<BasketTabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketTabViewHolder
        = BasketTabViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketTabViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 1
}