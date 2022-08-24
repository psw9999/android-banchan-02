package com.example.banchan.presentation.home.best

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBestBinding
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.best.BestWrapAdapter
import com.example.banchan.presentation.adapter.home.HomeHeaderAdapter
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BestFragment : HomeTabFragment<FragmentBestBinding>(R.layout.fragment_best) {

    private val bestViewModel: BestViewModel by viewModels()
    private val mainHeaderAdapter by lazy {
        HomeHeaderAdapter(isSubtitleVisible = true, titleRes = R.string.home_best_title)
    }
    private val bestWrapAdapter by lazy {
        BestWrapAdapter(basketIconClickListener, detailClickListener)
    }

    override fun initViews() {
        binding.viewModel = bestViewModel
        initRecyclerView()
        binding.layoutErrorBest.btnHomeErrorReload.setOnClickListener {
            bestViewModel.refresh()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bestViewModel.bestUiState.collectLatest { uiState ->
                    if(uiState is UiState.Success) {
                        bestWrapAdapter.submitList(uiState.item)
                    }
                }
            }
        }
    }

    private fun initRecyclerView(){
        binding.rvBestList.adapter = ConcatAdapter(mainHeaderAdapter, bestWrapAdapter)
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
    }

}
