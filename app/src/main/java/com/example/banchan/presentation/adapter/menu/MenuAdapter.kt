package com.example.banchan.presentation.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.databinding.ItemMenuBinding

class MenuAdapter(
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) : ListAdapter<ItemModel, MenuViewHolder>(DiffCallback()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position), basketClickListener, productDetailListener)
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.setItem(getItem(position))
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

}