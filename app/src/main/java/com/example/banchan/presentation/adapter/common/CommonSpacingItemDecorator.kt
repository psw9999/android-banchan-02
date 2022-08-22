package com.example.banchan.presentation.adapter.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CommonSpacingItemDecorator(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.top = padding
            outRect.bottom = padding
            outRect.left = padding
            outRect.right = padding
        }
    }
}