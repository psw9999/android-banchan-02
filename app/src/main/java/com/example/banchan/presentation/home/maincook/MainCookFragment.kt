package com.example.banchan.presentation.home.maincook

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentMainCookBinding
import com.example.banchan.presentation.adapter.main.*
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainCookFragment : HomeTabFragment<FragmentMainCookBinding>(R.layout.fragment_main_cook) {

    private val viewModel by viewModels<MainCookViewModel>()

    private val mainAdapter by lazy {
        MainAdapter(
            onTypeChanged = {
                viewModel.changeType(it)
            },
            onFilterChanged = {
                viewModel.changeFilter(it)
            },
            basketIconClickListener,
            detailClickListener
        )
    }

    override fun initViews() {
        binding.rvMain.apply {
            adapter = mainAdapter
            itemAnimator = null
        }
        changeListType(Type.Grid)
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dishes.combine(basketViewModel.basketList) { dishes, basketList ->
                    dishes.map { dish ->
                        if (dish is MainItemListModel.SmallItem) {
                            dish.copy(
                                item = dish.item.copy(
                                    isCartAdded = dish.item.detailHash in basketList.map { it.detailHash }
                                )
                            )
                        } else if (dish is MainItemListModel.LargeItem) {
                            dish.copy(
                                item = dish.item.copy(
                                    isCartAdded = dish.item.detailHash in basketList.map { it.detailHash }
                                )
                            )
                        } else {
                            dish
                        }
                    }
                }.collectLatest {
                    mainAdapter.submitList(it) {
                        changeListType(viewModel.type)
                    }
                }
            }
        }
    }

    private val commonSpacingItemDecorator by lazy {
        CommonSpacingItemDecorator(
            dpToPx(
                requireActivity(),
                12
            )
        )
    }

    private val gridSpacingItemDecorator by lazy {
        GridSpacingItemDecorator(
            dpToPx(
                requireActivity(),
                12
            )
        )
    }

    private fun changeListType(type: Type) {
        when (type) {
            Type.Linear -> {
                binding.rvMain.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    if (itemDecorationCount != 0) {
                        removeItemDecorationAt(0)
                    }
                    addItemDecoration(commonSpacingItemDecorator)
                }
            }
            Type.Grid -> {
                binding.rvMain.apply {
                    layoutManager = GridLayoutManager(requireActivity(), 2)
                    if (itemDecorationCount != 0) {
                        removeItemDecorationAt(0)
                    }
                    addItemDecoration(gridSpacingItemDecorator)
                }
                (binding.rvMain.layoutManager as GridLayoutManager).spanSizeLookup =
                    object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return when (mainAdapter.getItemViewType(position)) {
                                MainAdapter.HEADER_VIEW_TYPE, MainAdapter.FILTER_VIEW_TYPE, MainAdapter.EMPTY_VIEW_TYPE, MainAdapter.ERROR_VIEW_TYPE, MainAdapter.LOADING_VIEW_TYPE -> 2
                                else -> 1
                            }
                        }
                    }
            }
        }
    }
}