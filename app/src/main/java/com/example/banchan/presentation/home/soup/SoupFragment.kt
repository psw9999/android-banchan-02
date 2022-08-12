package com.example.banchan.presentation.home.soup

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentSoupBinding
import com.example.banchan.presentation.adapter.home.CommonAdapter
import com.example.banchan.presentation.adapter.main.SpacingItemDecorator
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SoupFragment : BaseFragment<FragmentSoupBinding>(R.layout.fragment_soup) {
    private val viewModel by viewModels<SoupViewModel>()
    private val soupAdapter by lazy {
        CommonAdapter {
            viewModel.changeFilter(it)
        }
    }

    override fun initViews() {
        binding.rvSoup.apply {
            adapter = soupAdapter
            itemAnimator = null
            addItemDecoration(SpacingItemDecorator(dpToPx(requireActivity(), 4)))
        }

        (binding.rvSoup.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (soupAdapter.getItemViewType(position)) {
                        CommonAdapter.HEADER_VIEW_TYPE, CommonAdapter.FILTER_VIEW_TYPE, CommonAdapter.EMPTY_VIEW_TYPE, CommonAdapter.ERROR_VIEW_TYPE, CommonAdapter.LOADING_VIEW_TYPE -> 2
                        else -> 1
                    }
                }
            }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dishes.collect {
                    soupAdapter.submitList(it)
                }
            }
        }
    }
}