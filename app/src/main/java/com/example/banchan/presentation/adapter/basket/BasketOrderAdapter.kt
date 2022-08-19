package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.OrderModel

class BasketOrderAdapter: RecyclerView.Adapter<BasketOrderViewHolder>() {

    private var orderModel: OrderModel? = null

    fun setOrderModel(orderModel: OrderModel){
        this.orderModel = orderModel
        notifyItemChanged(0, true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketOrderViewHolder
        = BasketOrderViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketOrderViewHolder, position: Int) {
        orderModel?.let{ holder.bind(it) }
    }

    override fun onBindViewHolder(
        holder: BasketOrderViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.bind(orderModel!!)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun getItemCount(): Int = 1
}