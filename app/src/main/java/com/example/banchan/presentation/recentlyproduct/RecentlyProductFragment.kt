package com.example.banchan.presentation.recentlyproduct

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.FragmentRecentlyProductBinding
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.common.CommonGridSpacingItemDecorator
import com.example.banchan.presentation.adapter.recentlyproduct.RecentlyProductAdapter
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentlyProductFragment :
    HomeTabFragment<FragmentRecentlyProductBinding>(R.layout.fragment_recently_product) {
    private var job: Job? = null
    private val viewModel: RecentlyProductViewModel by activityViewModels()
    private val recentlyProductAdapter by lazy {
        RecentlyProductAdapter(basketIconClickListener) {
            job = lifecycleScope.launch(start = CoroutineStart.LAZY) {
                binding.rvRecentlyProduct.scrollToPosition(0)
            }
            detailClickListener(it)
        }
    }

    override fun initViews() {
        binding.rvRecentlyProduct.apply {
            adapter = recentlyProductAdapter
            addItemDecoration(CommonGridSpacingItemDecorator(dpToPx(requireActivity(), 16)))
        }

        binding.toolbarBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            recentlyProductAdapter.submitList(it.item) {
                                job?.start()
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "recently_product"
    }
}