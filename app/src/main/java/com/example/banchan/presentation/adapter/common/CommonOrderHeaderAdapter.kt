package com.example.banchan.presentation.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.presentation.ordersuccess.OrderCommonListModel

class CommonOrderHeaderAdapter : RecyclerView.Adapter<CommonOrderHeaderViewHolder>() {
    private var headerModel: OrderCommonListModel.Header? = null

    fun updateHeader(header: OrderCommonListModel.Header) {
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