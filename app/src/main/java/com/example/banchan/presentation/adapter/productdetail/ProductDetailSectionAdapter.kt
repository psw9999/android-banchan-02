package com.example.banchan.presentation.adapter.productdetail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductDetailSectionAdapter(
) : RecyclerView.Adapter<ProductDetailSectionViewHolder>() {

    private val sectionList: MutableList<String> = mutableListOf()

    fun setSectionList(sectionList: List<String>){
        this.sectionList.clear()
        this.sectionList.addAll(sectionList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailSectionViewHolder =
        ProductDetailSectionViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProductDetailSectionViewHolder, position: Int) {
        holder.bind(sectionList[position])
    }

    override fun getItemCount(): Int = sectionList.size

}