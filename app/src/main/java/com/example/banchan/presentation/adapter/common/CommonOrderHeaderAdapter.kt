package com.example.banchan.presentation.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.presentation.ordersuccess.OrderSuccessListModel

class CommonOrderHeaderAdapter : RecyclerView.Adapter<CommonOrderHeaderViewHolder>() {
    private var headerModel: OrderSuccessListModel.Header? = null

    fun updateHeader(header: OrderSuccessListModel.Header) {
        headerModel = header
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonOrderHeaderViewHolder = CommonOrderHeaderViewHolder.create(parent)

    override fun onBindViewHolder(holder: CommonOrderHeaderViewHolder, position: Int) {
        headerModel?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return 1
    }
}