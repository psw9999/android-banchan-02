package com.example.banchan.presentation.adapter.best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.*
import com.example.banchan.domain.model.BestListItem
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.adapter.home.HomeEmptyViewHolder
import com.example.banchan.presentation.adapter.home.HomeErrorViewHolder
import com.example.banchan.presentation.adapter.home.HomeHeaderViewHolder
import com.example.banchan.presentation.adapter.home.HomeLoadingViewHolder
import com.example.banchan.presentation.adapter.menu.MenuAdapter

class BestListAdapter(
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) : ListAdapter<BestListItem, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BestListItem>() {
            override fun areItemsTheSame(oldItem: BestListItem, newItem: BestListItem): Boolean {
                return if (oldItem is BestListItem.BestContent && newItem is BestListItem.BestContent) {
                    oldItem.bestItem.title == oldItem.bestItem.title
                } else {
                    oldItem == newItem
                }
            }

            override fun areContentsTheSame(oldItem: BestListItem, newItem: BestListItem): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: BestListItem, newItem: BestListItem): Any? {
                return (oldItem is BestListItem.BestContent && newItem is BestListItem.BestContent)
            }
        }

    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is BestListItem.BestHeader -> R.layout.item_home_header
            is BestListItem.BestContent -> R.layout.item_best_list
            is BestListItem.BestError -> R.layout.item_home_error
            is BestListItem.BestLoading -> R.layout.item_home_loading
            is BestListItem.BestEmpty -> R.layout.item_home_empty
            else -> throw UnsupportedOperationException("Unknown view")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_home_header -> HomeHeaderViewHolder.create(parent)
            R.layout.item_best_list -> BestContentViewHolder(
                ItemBestListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), viewPool, basketClickListener, productDetailListener
            )
            R.layout.item_home_empty -> HomeEmptyViewHolder.create(parent)
            R.layout.item_home_error -> HomeErrorViewHolder.create(parent)
            R.layout.item_home_loading -> HomeLoadingViewHolder.create(parent)
            else -> throw UnsupportedOperationException("Unknown view")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).let { bestListItem ->
            when (bestListItem) {
                is BestListItem.BestHeader -> (holder as HomeHeaderViewHolder).bind(
                    bestListItem.isSubTitleVisible, bestListItem.titleStrRes
                )
                is BestListItem.BestLoading -> (holder as HomeLoadingViewHolder)
                is BestListItem.BestEmpty -> (holder as HomeEmptyViewHolder)
                is BestListItem.BestError -> (holder as HomeErrorViewHolder)
                is BestListItem.BestContent -> (holder as BestContentViewHolder).initBind(
                    bestListItem
                )
                else -> throw UnsupportedOperationException("Unknown view")
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val item = getItem(position)
            if ((payloads[0] as Boolean) && (item is BestListItem.BestContent)) {
                (holder as BestContentViewHolder).itemsBind(item.bestItem.items)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }
}

class BestContentViewHolder(
    private val binding: ItemBestListBinding,
    private val pool: RecyclerView.RecycledViewPool,
    private val basketClickListener: (ItemModel) -> Unit,
    private val productDetailListener: (ItemModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun initBind(bestModel: BestListItem.BestContent) {
        binding.title = bestModel.bestItem.title
        binding.rvBestList.setRecycledViewPool(pool)

        val layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false).apply {
                this.initialPrefetchItemCount = 3
            }
        binding.rvBestList.layoutManager = layoutManager

        val menuAdapter = MenuAdapter(basketClickListener, productDetailListener)
        binding.rvBestList.adapter = menuAdapter
        menuAdapter.submitList(bestModel.bestItem.items)
    }

    fun itemsBind(items: List<ItemModel>) {
        val menuAdapter = binding.rvBestList.adapter as MenuAdapter
        menuAdapter.submitList(items)
    }

}