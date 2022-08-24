package com.example.banchan.presentation.home.soup

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentSoupBinding
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.common.CommonAdapter
import com.example.banchan.presentation.adapter.common.CommonFilterAdapter
import com.example.banchan.presentation.adapter.home.HomeHeaderAdapter
import com.example.banchan.presentation.adapter.main.MainDishGridItemDecorator
import com.example.banchan.presentation.adapter.main.MainItemListModel
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SoupFragment : HomeTabFragment<FragmentSoupBinding>(R.layout.fragment_soup) {
    private val viewModel by viewModels<SoupViewModel>()
    private val soupHeaderAdapter by lazy {
        HomeHeaderAdapter(isSubtitleVisible = false, titleRes = R.string.home_soup_title)
    }
    private val soupFilterAdapter by lazy {
        CommonFilterAdapter { viewModel.changeFilter(it) }
    }
    private val soupItemAdapter by lazy {
        CommonAdapter(
            basketClickListener = basketIconClickListener,
            productDetailListener = detailClickListener
        )
    }

    override fun initViews() {
        binding.rvSoup.apply {
            adapter = ConcatAdapter(
                soupHeaderAdapter,
                soupFilterAdapter,
                soupItemAdapter
            )
            itemAnimator = null
            addItemDecoration(MainDishGridItemDecorator(dpToPx(requireActivity(), 12)))
        }

        (binding.rvSoup.layoutManager as GridLayoutManager).spanSizeLookup =
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
                            soupFilterAdapter.updateState(
                                viewModel.filter.value,
                                it.item.size
                            )
                            soupItemAdapter.submitList(it.item)
                        }
                        is UiState.Empty -> {
                            binding.setUiVisible(it)
                        }
                        is UiState.Error -> {
                            binding.setUiVisible(it)
                            binding.layoutErrorSoup.btnHomeErrorReload.setOnClickListener {
                                viewModel.refresh()
                            }
                        }
                    }
                }
        }
    }

    private fun FragmentSoupBinding.setUiVisible(uiState: UiState<List<ItemModel>>) {
        progressSoup.isVisible = uiState is UiState.Loading
        layoutErrorSoup.root.isVisible = uiState is UiState.Error
        layoutEmptySoup.root.isVisible = uiState is UiState.Empty
    }

    companion object {
        const val TAG = "soup"
    }
}