package com.example.banchan.presentation.adapter.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.banchan.domain.model.BasketModel

class BasketListAdapter(
    private val onCheckBoxClick: ((BasketModel) -> Unit),
    private val onClickDeleteBtn: ((BasketModel) -> Unit),
    private val onClickMinusBtn: ((BasketModel) -> Unit),
    private val onClickPlusBtn: ((BasketModel) -> Unit)
) : ListAdapter<BasketModel, BasketItemViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BasketModel>() {
            override fun areItemsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(oldItem: BasketModel, newItem: BasketModel): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: BasketModel, newItem: BasketModel): Any? {
                return (oldItem.count != newItem.count) || (oldItem.isChecked != newItem.isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder =
        BasketItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: BasketItemViewHolder, position: Int) {
        holder.initBind(
            getItem(position),
            onCheckBoxClick,
            onClickDeleteBtn,
            onClickMinusBtn,
            onClickPlusBtn
        )
    }

    override fun onBindViewHolder(
        holder: BasketItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] as Boolean) holder.initBind(
                getItem(position),
                onCheckBoxClick,
                onClickDeleteBtn,
                onClickMinusBtn,
                onClickPlusBtn
            )
            else super.onBindViewHolder(holder, position, payloads)
        }
    }
}