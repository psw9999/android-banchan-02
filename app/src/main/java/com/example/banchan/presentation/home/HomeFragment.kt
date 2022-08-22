package com.example.banchan.presentation.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.FragmentHomeBinding
import com.example.banchan.presentation.adapter.home.HomeViewPagerAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.presentation.main.FragmentType
import com.example.banchan.presentation.main.MainViewModel
import com.example.banchan.util.ext.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val basketViewModel: BasketViewModel by activityViewModels()
    private val orderStateViewModel: OrderStateViewModel by activityViewModels()

    override fun initViews() {
        initViewPager()
        binding.abHome.setOnCartClickListener {
            mainViewModel.setCurrentFragment(FragmentType.Basket)
        }
        binding.abHome.setOnProfileClickListener {
            mainViewModel.setCurrentFragment(FragmentType.OrderList)
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    basketViewModel.basketFlow.collectLatest { result ->
                        result.onSuccess { binding.abHome.setCartCount(it.size) }
                        result.onFailure { requireContext().toast(getString(R.string.basket_get_error)) }
                    }
                }

                launch {
                    orderStateViewModel.isOrderingStateFlow.collect { isAllOrderSuccess ->
                        binding.abHome.setIsShipping(!isAllOrderSuccess)
                    }
                }

            }
        }
    }

    private fun initViewPager() {
        val adapter =
            HomeViewPagerAdapter(this, resources.getStringArray(R.array.home_tab_title_array))
        binding.vpHome.adapter = adapter
        TabLayoutMediator(binding.tlHome, binding.vpHome) { tab, position ->
            tab.text = adapter.homeTabTitleArray[position]
        }.attach()
    }

}