package com.example.banchan.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemHomeErrorBinding

class HomeErrorViewHolder(binding: ItemHomeErrorBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup) = HomeErrorViewHolder(
            ItemHomeErrorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}