package com.example.banchan.presentation.recentlyproduct

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.banchan.R
import com.example.banchan.databinding.FragmentRecentlyProductBinding
import com.example.banchan.presentation.adapter.common.CommonGridSpacingItemDecorator
import com.example.banchan.presentation.adapter.recentlyproduct.RecentlyProductPagingAdapter
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentlyProductFragment :
    HomeTabFragment<FragmentRecentlyProductBinding>(R.layout.fragment_recently_product) {
    private val viewModel: RecentlyProductViewModel by activityViewModels()
    private lateinit var recentlyProductAdapter: RecentlyProductPagingAdapter

    override fun initViews() {
        recentlyProductAdapter = RecentlyProductPagingAdapter(basketIconClickListener) {
            detailClickListener(it)
        }
        binding.viewModel = viewModel
        binding.rvRecentlyProduct.apply {
            adapter = recentlyProductAdapter
            addItemDecoration(CommonGridSpacingItemDecorator(dpToPx(requireActivity(), 16)))
        }

        binding.toolbarBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.layoutErrorRecent.btnHomeErrorReload.setOnClickListener {
            recentlyProductAdapter.refresh()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pagingData.collectLatest {
                        recentlyProductAdapter.submitData(it)
                    }
                }

                launch {
                    recentlyProductAdapter.loadStateFlow.collectLatest {
                        val isListEmpty =
                            it.refresh is LoadState.NotLoading && recentlyProductAdapter.itemCount == 0
                        binding.progressRecent.isVisible = it.refresh is LoadState.Loading
                        binding.layoutEmptyRecent.root.isVisible = isListEmpty
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "recently_product"
    }
}