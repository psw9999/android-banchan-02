package com.example.banchan.presentation.adapter.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.banchan.databinding.ItemDetailThumbnailBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProductThumbNailViewHolder(
    private val binding: ItemDetailThumbnailBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrls: List<String>, onThumbNailChange: ((Int) -> Unit), position: Int) {
        binding.vpDetailThumbnail.adapter = ProductDetailSliderAdapter(imageUrls)
        binding.vpDetailThumbnail.setCurrentItem(position, false)
        binding.vpDetailThumbnail.registerOnPageChangeCallback( object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                onThumbNailChange(position)
            }
        })
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