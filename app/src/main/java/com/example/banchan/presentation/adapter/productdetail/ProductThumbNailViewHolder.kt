package com.example.banchan.presentation.adapter.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemDetailThumbnailBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProductThumbNailViewHolder(
    private val binding: ItemDetailThumbnailBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrls : List<String>) {
        binding.vpDetailThumbnail.adapter = ProductDetailSliderAdapter(imageUrls)
        TabLayoutMediator(binding.tlDetailSelector, binding.vpDetailThumbnail) { tab, position ->
        }.attach()
    }

    companion object {
        fun create(parent: ViewGroup) = ProductThumbNailViewHolder(
            ItemDetailThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}