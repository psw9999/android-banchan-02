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
import com.example.banchan.presentation.adapter.home.HomeEmptyViewHolder
import com.example.banchan.presentation.adapter.home.HomeErrorViewHolder
import com.example.banchan.presentation.adapter.home.HomeHeaderViewHolder
import com.example.banchan.presentation.adapter.home.HomeLoadingViewHolder
import com.example.banchan.presentation.adapter.menu.MenuAdapter

class BestListAdapter :
    ListAdapter<BestListItem, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BestListItem>() {
            override fun areItemsTheSame(oldItem: BestListItem, newItem: BestListItem): Boolean {
                return if (oldItem is BestListItem.BestContent && newItem is BestListItem.BestContent) {
                    oldItem.bestItem.items == newItem.bestItem.items
                } else {
                    oldItem == newItem
                }
            }

            override fun areContentsTheSame(oldItem: BestListItem, newItem: BestListItem): Boolean =
                oldItem == newItem
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
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_home_header -> HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_best_list -> BestContentViewHolder(
                ItemBestListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), viewPool
            )
            R.layout.item_home_empty -> HomeEmptyViewHolder(
                ItemHomeEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_home_error -> HomeErrorViewHolder(
                ItemHomeErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_home_loading -> HomeLoadingViewHolder(
                ItemHomeLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw UnsupportedOperationException("Unknown view")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).let { bestListItem ->
            when (bestListItem) {
                is BestListItem.BestHeader -> (holder as HomeHeaderViewHolder).bind(
                    bestListItem.isSubTitleVisible, bestListItem.title
                )
                is BestListItem.BestLoading -> (holder as HomeLoadingViewHolder)
                is BestListItem.BestEmpty -> (holder as HomeEmptyViewHolder)
                is BestListItem.BestError -> (holder as HomeErrorViewHolder)
                is BestListItem.BestContent -> (holder as BestContentViewHolder).bind(
                    bestListItem
                )
            }
        }
    }
}

class BestContentViewHolder(
    private val binding: ItemBestListBinding,
    private val pool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bestModel: BestListItem.BestContent) {
        binding.title = bestModel.bestItem.title
        binding.rvBestList.setRecycledViewPool(pool)

        val layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false).apply {
                this.initialPrefetchItemCount = 3
            }
        binding.rvBestList.layoutManager = layoutManager

        val menuAdapter = MenuAdapter()
        binding.rvBestList.adapter = menuAdapter
        menuAdapter.submitList(bestModel.bestItem.items)
    }
}