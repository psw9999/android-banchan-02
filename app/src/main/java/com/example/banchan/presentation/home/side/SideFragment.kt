package com.example.banchan.presentation.home.side

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentSideBinding
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.common.CommonAdapter
import com.example.banchan.presentation.adapter.common.CommonFilterAdapter
import com.example.banchan.presentation.adapter.home.HomeHeaderAdapter
import com.example.banchan.presentation.adapter.main.MainDishGridItemDecorator
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SideFragment : HomeTabFragment<FragmentSideBinding>(R.layout.fragment_side) {
    private val viewModel by viewModels<SideViewModel>()
    private val sideHeaderAdapter by lazy {
        HomeHeaderAdapter(isSubtitleVisible = false, titleRes = R.string.home_side_title)
    }
    private val sideFilterAdapter by lazy {
        CommonFilterAdapter { viewModel.changeFilter(it) }
    }
    private val sideItemAdapter by lazy {
        CommonAdapter(
            basketClickListener = basketIconClickListener,
            productDetailListener = detailClickListener
        )
    }

    override fun initViews() {
        binding.rvSide.apply {
            adapter = ConcatAdapter(
                sideHeaderAdapter,
                sideFilterAdapter,
                sideItemAdapter
            )
            itemAnimator = null
            addItemDecoration(MainDishGridItemDecorator(dpToPx(requireActivity(), 12)))
        }

        (binding.rvSide.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position == 0 || position == 1) return 2
                    return 1
                }
            }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is UiState.Init -> {}
                        is UiState.Loading -> {
                            binding.setUiVisible(it)
                        }
                        is UiState.Success -> {
                            binding.setUiVisible(it)
                            sideFilterAdapter.updateState(
                                viewModel.filter.value,
                                it.item.size
                            )
                            sideItemAdapter.submitList(it.item)
                        }
                        is UiState.Empty -> {
                            binding.setUiVisible(it)
                        }
                        is UiState.Error -> {
                            binding.setUiVisible(it)
                            binding.layoutErrorSide.btnHomeErrorReload.setOnClickListener {
                                viewModel.refresh()
                            }
                        }
                    }
                }
        }
    }

    private fun FragmentSideBinding.setUiVisible(uiState: UiState<List<ItemModel>>) {
        progressSide.isVisible = uiState is UiState.Loading
        layoutErrorSide.root.isVisible = uiState is UiState.Error
        layoutEmptySide.root.isVisible = uiState is UiState.Empty
    }
}