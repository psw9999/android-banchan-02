package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.BasketModel

class BasketListAdapter(

) : ListAdapter<BasketModel, BasketItemViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BasketModel>() {
            override fun areItemsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder
        = BasketItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketItemViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}