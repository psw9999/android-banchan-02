package com.example.banchan.presentation.home.maincook

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentMainCookBinding
import com.example.banchan.presentation.adapter.main.CommonSpacingItemDecorator
import com.example.banchan.presentation.adapter.main.GridSpacingItemDecorator
import com.example.banchan.presentation.adapter.main.MainAdapter
import com.example.banchan.presentation.adapter.main.Type
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainCookFragment : HomeTabFragment<FragmentMainCookBinding>(R.layout.fragment_main_cook) {

    private val viewModel by viewModels<MainCookViewModel>()
    var typeChangeJob: Job? = null
    private val mainAdapter by lazy {
        MainAdapter(
            onTypeChanged = {
                viewModel.changeType(it)
                typeChangeJob =
                    viewLifecycleOwner.lifecycleScope.launch(start = CoroutineStart.LAZY) {
                        changeListType(it)
                    }
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
        changeListType(viewModel.type)
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.mainItemListModel.collectLatest {
                        mainAdapter.submitList(it) {
                            typeChangeJob?.start()
                        }
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