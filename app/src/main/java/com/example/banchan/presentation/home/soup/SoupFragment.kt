package com.example.banchan.presentation.home.soup

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentSoupBinding
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
class SoupFragment : HomeTabFragment<FragmentSoupBinding>(R.layout.fragment_soup) {
    private val viewModel by viewModels<SoupViewModel>()
    private lateinit var soupHeaderAdapter: HomeHeaderAdapter
    private lateinit var soupFilterAdapter: CommonFilterAdapter
    private lateinit var soupItemAdapter: CommonAdapter

    override fun initViews() {
        soupHeaderAdapter =
            HomeHeaderAdapter(isSubtitleVisible = false, titleRes = R.string.home_soup_title)
        soupFilterAdapter = CommonFilterAdapter { viewModel.changeFilter(it) }
        soupItemAdapter = CommonAdapter(
            basketClickListener = basketIconClickListener,
            productDetailListener = detailClickListener
        )

        binding.viewModel = viewModel
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

        binding.layoutErrorSoup.btnHomeErrorReload.setOnClickListener {
            viewModel.refresh()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it is UiState.Success) {
                        soupFilterAdapter.updateState(
                            viewModel.filter.value,
                            it.item.size
                        )
                        soupItemAdapter.submitList(it.item)
                    }
                }
        }
    }
}