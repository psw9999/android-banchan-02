package com.example.banchan.presentation.adapter.productdetail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductDetailThumbNailAdapter: RecyclerView.Adapter<ProductThumbNailViewHolder>() {

    private val thumbImageUrls: MutableList<String> = mutableListOf()

    fun setThumbImageUrls(thumbImageUrls: List<String>){
        this.thumbImageUrls.clear()
        this.thumbImageUrls.addAll(thumbImageUrls)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductThumbNailViewHolder =
        ProductThumbNailViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProductThumbNailViewHolder, position: Int) {
        holder.bind(thumbImageUrls)
    }

    override fun getItemCount(): Int = 1

}