package com.example.banchan.presentation.adapter.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CommonGridSpacingItemDecorator(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val col = position % 2

        outRect.top = padding
        outRect.bottom = padding

        if (col == 0) {
            outRect.left = padding
            outRect.right = padding / 2
        } else {
            outRect.left = padding / 2
            outRect.right = padding
        }
    }
}