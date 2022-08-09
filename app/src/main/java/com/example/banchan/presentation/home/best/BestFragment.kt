package com.example.banchan.presentation.home.best

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBestBinding
import com.example.banchan.fakeBestItem
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.home.best.adapter.BestListAdapter
import com.example.banchan.util.dimen.dpToPx

class BestFragment : BaseFragment<FragmentBestBinding>(R.layout.fragment_best) {

    private val bestListAdapter by lazy { BestListAdapter() }

    override fun initViews() {
        initRecyclerView()
    }

    override fun observe() {

    }

    private fun initRecyclerView(){
        binding.rvBestList.adapter = bestListAdapter
        binding.rvBestList.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) outRect.bottom = dpToPx(requireContext(), 24)
                else outRect.bottom = dpToPx(requireContext(), 40)
            }
        })
        bestListAdapter.submitList(fakeBestItem)
    }

}
