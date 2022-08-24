package com.example.banchan.presentation.adapter.best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.databinding.ItemBestListBinding
import com.example.banchan.domain.model.BestModel
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.RecyclerItemTouchInterceptorListener

class BestWrapViewHolder(
    private val binding: ItemBestListBinding,
    private val parent: ViewGroup
) : RecyclerView.ViewHolder(binding.root) {

    fun initBind(
        bestModel: BestModel,
        pool: RecyclerView.RecycledViewPool,
        basketClickListener: (ItemModel) -> Unit,
        productDetailListener: (ItemModel) -> Unit
    ) {
        binding.title = bestModel.title
        binding.rvBestList.setRecycledViewPool(pool)
        val bestItemAdapter = BestItemAdapter(basketClickListener, productDetailListener)
        binding.rvBestList.adapter = bestItemAdapter
        binding.rvBestList.addOnItemTouchListener(RecyclerItemTouchInterceptorListener(itemView.context, parent))
        bestItemAdapter.submitList(bestModel.items)
    }

    fun itemsBind(items: List<ItemModel>) {
        val bestItemAdapter = binding.rvBestList.adapter as BestItemAdapter
        bestItemAdapter.submitList(items)
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) = BestWrapViewHolder(
            ItemBestListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent
        )
    }
}