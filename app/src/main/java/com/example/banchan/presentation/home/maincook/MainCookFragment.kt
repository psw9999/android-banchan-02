package com.example.banchan.presentation.home.maincook

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banchan.R
import com.example.banchan.data.response.ItemListModel
import com.example.banchan.databinding.FragmentMainCookBinding
import com.example.banchan.fakeData
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.home.maincook.adapter.Filter
import com.example.banchan.presentation.home.maincook.adapter.MainAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainCookFragment : BaseFragment<FragmentMainCookBinding>(R.layout.fragment_main_cook) {
    private val viewModel by viewModels<MainCookViewModel>()

    private val mainAdapter by lazy {
        MainAdapter {
            changeListMode(it)
            viewModel.changeMode(it)
        }
    }

    override fun initViews() {
        binding.rvMain.apply {
            adapter = mainAdapter
            itemAnimator = null
        }
        changeListMode(Filter.Grid)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fake.collect {
                    mainAdapter.submitList(it)
                }
            }
        }
    }

    override fun observe() {
    }

    private fun changeListMode(type: Filter) {
        if (type == Filter.Linear) {
            binding.rvMain.layoutManager = LinearLayoutManager(requireActivity())
        } else {
            binding.rvMain.layoutManager = GridLayoutManager(requireActivity(), 2)
            (binding.rvMain.layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (mainAdapter.getItemViewType(position)) {
                            MainAdapter.HEADER_VIEW_TYPE -> 2
                            else -> 1
                        }
                    }
                }
        }
    }
}