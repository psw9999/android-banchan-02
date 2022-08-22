package com.example.banchan.presentation.adapter.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.presentation.home.maincook.Filter

class MainFilterAdapter(
    private val onTypeChanged: (Type) -> Unit,
    private val onFilterChanged: (Filter) -> Unit
) : RecyclerView.Adapter<MainFilterViewHolder>() {
    private var currentType: Type? = null
    private var currentFilter: Filter? = null

    fun updateState(type: Type? = null, filter: Filter? = null) {
        type?.let { currentType = it }
        filter?.let { currentFilter = it }
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFilterViewHolder = MainFilterViewHolder.create(parent)

    override fun onBindViewHolder(holder: MainFilterViewHolder, position: Int) {
        val type = currentType ?: return
        val filter = currentFilter ?: return
        holder.bind(
            currentType = type,
            onTypeChanged = onTypeChanged,
            currentFilter = filter,
            onFilterChanged = onFilterChanged
        )
    }

    override fun getItemCount(): Int {
        return 1
    }
}