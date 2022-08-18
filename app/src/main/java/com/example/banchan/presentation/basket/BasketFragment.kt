package com.example.banchan.presentation.basket

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBasketBinding
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.presentation.adapter.basket.BasketListAdapter
import com.example.banchan.presentation.adapter.basket.BasketOrderAdapter
import com.example.banchan.presentation.adapter.basket.BasketRecentlyTabAdapter
import com.example.banchan.presentation.adapter.basket.BasketTabAdapter
import com.example.banchan.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(R.layout.fragment_basket) {

    private val basketViewModel: BasketViewModel by viewModels()

    private val onCheckBoxClick: ((BasketModel) -> Unit) = { basketModel ->
        basketViewModel.updateBasketItem(basketModel)
    }

    private val onClickCheckBoxTab: ((Int) -> Unit) = { isSelected ->
        basketViewModel.updateAllBasketIsSelected(isSelected)
    }

    private val onClickSeletedDeleteBtn: (() -> Unit) = {
        basketViewModel.deleteSelectedBasketItems()
    }

    private val onClickDeleteBtn: ((BasketModel) -> Unit) = { basketModel ->
        basketViewModel.deleteBasketItem(basketModel)
    }

    private val basketListTabAdapter by lazy {
        BasketTabAdapter(
            onClickCheckBoxTab,
            onClickSeletedDeleteBtn
        )
    }
    private val basketListAdapter by lazy { BasketListAdapter(onCheckBoxClick, onClickDeleteBtn) }
    private val basketOrderAdapter by lazy { BasketOrderAdapter() }
    private val basketRecentlyTabAdapter by lazy { BasketRecentlyTabAdapter() }

    override fun initViews() {
        initRecyclerView()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    basketViewModel.basketItemFlow.collectLatest {
                        basketListAdapter.submitList(it)
                        basketViewModel.setIsBasketLoading(false)
                    }
                }

                launch {
                    basketViewModel.recentlyProductFlow.collectLatest {
                        basketRecentlyTabAdapter.setRecentlyViewedList(it)
                        basketViewModel.setIsRecentlyLoading(false)
                    }
                }

                launch {
                    basketViewModel.isLoading.collectLatest { isLoading ->
                        if (isLoading) {
                            binding.pbBasketLoading.visibility = View.VISIBLE
                            binding.rvBasketList.visibility = View.INVISIBLE
                        } else {
                            binding.pbBasketLoading.visibility = View.INVISIBLE
                            binding.rvBasketList.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvBasketList.adapter = ConcatAdapter(
            basketListTabAdapter,
            basketListAdapter,
            basketOrderAdapter,
            basketRecentlyTabAdapter
        )
    }

}