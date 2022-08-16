package com.example.banchan.presentation.adapter.productdetail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductDetailSliderAdapter(
    private val thumbImageUrls: List<String>
) : RecyclerView.Adapter<ProductDetailImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailImageViewHolder =
        ProductDetailImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProductDetailImageViewHolder, position: Int) {
        holder.bindImage(thumbImageUrls[position])
    }

    override fun getItemCount(): Int = thumbImageUrls.size
}