package com.example.banchan.presentation.recentlyproduct

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.FragmentRecentlyProductBinding
import com.example.banchan.presentation.adapter.common.CommonGridSpacingItemDecorator
import com.example.banchan.presentation.adapter.recentlyproduct.RecentlyProductAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.basket.BasketViewModel
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentlyProductFragment :
    BaseFragment<FragmentRecentlyProductBinding>(R.layout.fragment_recently_product) {
    private val viewModel by activityViewModels<BasketViewModel>()
    private val recentlyProductAdapter by lazy {
        RecentlyProductAdapter({}, {})
    }

    override fun initViews() {
        binding.rvRecentlyProduct.apply {
            adapter = recentlyProductAdapter
            itemAnimator = null
            addItemDecoration(CommonGridSpacingItemDecorator(dpToPx(requireActivity(), 16)))
        }

        binding.toolbarBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentlyProductFlow.collect {
                    recentlyProductAdapter.submitList(it)
                }
            }
        }
    }
}