package com.example.banchan.presentation.adapter.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemDetailImageSliderBinding

class ProductDetailImageViewHolder(
    private val binding: ItemDetailImageSliderBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = ProductDetailImageViewHolder(
            ItemDetailImageSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun bindImage(imageURL : String) {
        binding.imageUrl = imageURL
    }

}