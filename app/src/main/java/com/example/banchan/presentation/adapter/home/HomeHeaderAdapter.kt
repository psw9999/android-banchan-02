package com.example.banchan.presentation.adapter.home

import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.presentation.adapter.home.HomeHeaderViewHolder

class HomeHeaderAdapter(
    private val isSubtitleVisible: Boolean,
    @StringRes private val titleRes: Int
) : RecyclerView.Adapter<HomeHeaderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeHeaderViewHolder = HomeHeaderViewHolder.create(parent)

    override fun onBindViewHolder(holder: HomeHeaderViewHolder, position: Int) {
        holder.bind(isSubTitleVisible = isSubtitleVisible, titleStrRes = titleRes)
    }

    override fun getItemCount(): Int {
        return 1
    }
}