package com.example.banchan.presentation.home.side

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentSoupBinding
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.adapter.main.MainAdapter
import com.example.banchan.presentation.adapter.main.SpacingItemDecorator
import com.example.banchan.presentation.adapter.home.CommonAdapter
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SideFragment : BaseFragment<FragmentSoupBinding>(R.layout.fragment_soup) {
    private val viewModel by viewModels<SideViewModel>()
    private val sideAdapter by lazy {
        CommonAdapter {
            viewModel.changeFilter(it)
        }
    }

    override fun initViews() {
        binding.rvSoup.apply {
            adapter = sideAdapter
            itemAnimator = null
            addItemDecoration(SpacingItemDecorator(dpToPx(requireActivity(), 4)))
        }

        (binding.rvSoup.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (sideAdapter.getItemViewType(position)) {
                        MainAdapter.HEADER_VIEW_TYPE -> 2
                        else -> 1
                    }
                }
            }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.menus.collect {
                    sideAdapter.submitList(it)
                }
            }
        }
    }
}