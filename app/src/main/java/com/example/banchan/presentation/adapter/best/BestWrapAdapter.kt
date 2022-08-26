package com.example.banchan.presentation.adapter.best

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.domain.model.BestModel
import com.example.banchan.domain.model.ItemModel

class BestWrapAdapter(
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) : ListAdapter<BestModel, BestWrapViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BestModel>() {
            override fun areItemsTheSame(oldItem: BestModel, newItem: BestModel): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: BestModel, newItem: BestModel): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: BestModel, newItem: BestModel): Any? {
                return oldItem.items.map { it.isCartAdded } != newItem.items.map { it.isCartAdded }
            }
        }
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestWrapViewHolder =
        BestWrapViewHolder.create(parent)

    override fun onBindViewHolder(holder: BestWrapViewHolder, position: Int) {
        holder.initBind(
            getItem(position),
            viewPool,
            basketClickListener,
            productDetailListener
        )
    }

    override fun onBindViewHolder(
        holder: BestWrapViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) {
                holder.itemsBind(getItem(position).items)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

}