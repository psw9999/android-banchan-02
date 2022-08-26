package com.example.banchan.presentation.adapter.productdetail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.ProductDetailModel

class ProductInfoAdapter(
    private val onMinusClick: () -> Unit,
    private val onPlusClick: () -> Unit,
    private val onBasketAddClick: () -> Unit,
) : RecyclerView.Adapter<ProductInfoViewHolder>() {

    private var productInfo: ProductDetailModel? = null
    private var amount: Int = 1

    fun setProductInfo(productInfo: ProductDetailModel, amount : Int) {
        this.productInfo = productInfo
        this.amount = amount
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInfoViewHolder {
        return ProductInfoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductInfoViewHolder, position: Int) {
        holder.bind(productInfo, amount, onMinusClick, onPlusClick, onBasketAddClick)
    }

    override fun onBindViewHolder(
        holder: ProductInfoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val amount = payloads[0]
            if (amount is Int) {
                holder.bindAmount(amount)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun getItemCount(): Int = 1

}