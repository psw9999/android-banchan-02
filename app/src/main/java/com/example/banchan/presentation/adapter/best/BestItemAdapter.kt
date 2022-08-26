package com.example.banchan.presentation.adapter.best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.databinding.ItemMenuBinding
import com.example.banchan.domain.model.ItemModel

class BestItemAdapter(
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) : ListAdapter<ItemModel, BestItemViewHolder>(DiffCallback()) {

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: ItemModel, newItem: ItemModel): Any? {
                return oldItem.isCartAdded != newItem.isCartAdded
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemViewHolder {
        return BestItemViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BestItemViewHolder, position: Int) {
        holder.bind(getItem(position), basketClickListener, productDetailListener)
    }

    override fun onBindViewHolder(
        holder: BestItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.bind(getItem(position), basketClickListener, productDetailListener)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }
}