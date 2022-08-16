package com.example.banchan.presentation.adapter.productdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemDetailSectionBinding

class ProductDetailSectionViewHolder(
    private val binding: ItemDetailSectionBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = ProductDetailSectionViewHolder(
            ItemDetailSectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun bind(image : String) {
        binding.imageUrl = image
    }

}