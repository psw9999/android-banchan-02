package com.example.banchan.presentation.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.presentation.home.Filter

class CommonFilterAdapter(
    private val onFilterChanged: (Filter) -> Unit
) : RecyclerView.Adapter<CommonFilterViewViewHolder>() {
    private var currentFilter: Filter? = null
    private var currentTotal = 0

    fun updateState(filter: Filter? = null, total: Int? = 0) {
        filter?.let { currentFilter = it }
        total?.let { currentTotal = it }
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonFilterViewViewHolder = CommonFilterViewViewHolder.create(parent)

    override fun onBindViewHolder(holder: CommonFilterViewViewHolder, position: Int) {
        val filter = currentFilter ?: return
        holder.bind(
            currentFilter = filter,
            onFilterChanged = onFilterChanged,
            total = currentTotal
        )
    }

    override fun getItemCount(): Int {
        return 1
    }
}