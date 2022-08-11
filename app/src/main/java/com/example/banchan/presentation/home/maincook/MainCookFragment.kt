package com.example.banchan.presentation.home.maincook

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banchan.R
import com.example.banchan.databinding.FragmentMainCookBinding
import com.example.banchan.domain.model.ItemListModel
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.home.maincook.adapter.MainAdapter
import com.example.banchan.presentation.home.maincook.adapter.SpacingItemDecorator
import com.example.banchan.presentation.home.maincook.adapter.Type
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainCookFragment : BaseFragment<FragmentMainCookBinding>(R.layout.fragment_main_cook) {
    private val viewModel by viewModels<MainCookViewModel>()

    private val mainAdapter by lazy {
        MainAdapter {
            viewModel.changeType(it)
        }
    }

    override fun initViews() {
        binding.rvMain.apply {
            adapter = mainAdapter
            itemAnimator = null
            addItemDecoration(SpacingItemDecorator(dpToPx(requireActivity(), 4)))
        }
        changeListType(Type.Grid)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.menus.collect {
                    mainAdapter.submitList(it) {
                        changeListType((it[0] as ItemListModel.Header).currentType)
                    }
                }
            }
        }
    }

    override fun observe() {
    }

    private fun changeListType(type: Type) {
        when (type) {
            Type.Linear -> {
                binding.rvMain.layoutManager = LinearLayoutManager(requireActivity())
            }
            Type.Grid -> {
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
}