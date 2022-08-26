package com.example.banchan.presentation.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.presentation.ordersuccess.OrderSuccessListModel

class CommonOrderFooterAdapter : RecyclerView.Adapter<CommonOrderFooterViewHolder>() {
    private var footerModel: OrderSuccessListModel.Footer? = null

    fun updateFooter(footer: OrderSuccessListModel.Footer){
        footerModel = footer
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonOrderFooterViewHolder = CommonOrderFooterViewHolder.create(parent)

    override fun onBindViewHolder(holder: CommonOrderFooterViewHolder, position: Int) {
        footerModel?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return 1
    }
}