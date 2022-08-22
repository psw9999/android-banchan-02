package com.example.banchan.presentation.home.maindish

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentMainDishBinding
import com.example.banchan.presentation.UiState
import com.example.banchan.presentation.adapter.common.CommonSpacingItemDecorator
import com.example.banchan.presentation.adapter.main.*
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainDishFragment : HomeTabFragment<FragmentMainDishBinding>(R.layout.fragment_main_dish) {
    private val viewModel by viewModels<MainDishViewModel>()
    private var typeChangeJob: Job? = null
    private val mainHeaderAdapter by lazy {
        MainHeaderAdapter(R.string.home_main_cook_title)
    }
    private val mainFilterAdapter by lazy {
        MainFilterAdapter(
            onTypeChanged = {
                viewModel.changeType(it)
                typeChangeJob =
                    viewLifecycleOwner.lifecycleScope.launch(start = CoroutineStart.LAZY) {
                        changeListType(it)
                    }
            },
            onFilterChanged = {
                viewModel.changeFilter(it)
            }
        )
    }
    private val mainItemAdapter by lazy {
        MainItemAdapter(
            basketClickListener = basketIconClickListener,
            productDetailListener = detailClickListener
        )
    }

    override fun initViews() {
        binding.rvMain.apply {
            adapter = ConcatAdapter(
                mainHeaderAdapter,
                mainFilterAdapter,
                mainItemAdapter
            )
            itemAnimator = null
        }
        changeListType(viewModel.type.value)
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
                            mainFilterAdapter.updateState(
                                viewModel.type.value,
                                viewModel.filter.value
                            )
                            mainItemAdapter.submitList(it.item) {
                                typeChangeJob?.start()
                            }
                        }
                        is UiState.Empty -> {
                            binding.setUiVisible(it)
                        }
                        is UiState.Error -> {
                            binding.setUiVisible(it)
                            binding.layoutErrorMain.btnHomeErrorReload.setOnClickListener {
                                viewModel.refresh()
                            }
                        }
                    }
                }
        }
    }

    private fun FragmentMainDishBinding.setUiVisible(uiState: UiState<List<MainItemListModel>>) {
        progressMainDish.isVisible = uiState is UiState.Loading
        layoutErrorMain.root.isVisible = uiState is UiState.Error
        layoutEmptyMain.root.isVisible = uiState is UiState.Empty
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
        MainDishGridItemDecorator(
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
                            if (position == 0 || position == 1) return 2
                            return 1
                        }
                    }
            }
        }
    }
}