package com.example.banchan.presentation.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.adapter.main.MediumMenuViewHolder

class CommonAdapter(
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) :
    ListAdapter<ItemModel, MediumMenuViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumMenuViewHolder {
        return MediumMenuViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MediumMenuViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            basketClickListener,
            productDetailListener
        )
    }

    override fun onBindViewHolder(
        holder: MediumMenuViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val commonListItem = getItem(position)
            if (payloads[0] as Boolean) {
                holder.setItem(commonListItem)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(
                oldItem: ItemModel,
                newItem: ItemModel
            ): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(
                oldItem: ItemModel,
                newItem: ItemModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(
                oldItem: ItemModel,
                newItem: ItemModel
            ): Any? {
                return if (oldItem.isCartAdded != newItem.isCartAdded) true
                else null
            }
        }
    }
}