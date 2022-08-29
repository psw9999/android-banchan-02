package com.example.banchan.presentation.home

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.FragmentHomeBinding
import com.example.banchan.presentation.adapter.home.HomeViewPagerAdapter
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.basket.BasketFragment
import com.example.banchan.presentation.main.AppBarViewModel
import com.example.banchan.presentation.orderlist.OrderListFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val appBarViewModel: AppBarViewModel by activityViewModels()

    override fun initViews() {
        initViewPager()
        binding.abHome.setOnCartClickListener {
            navigateToBasket()
        }
        binding.abHome.setOnProfileClickListener {
            navigateToProfile()
        }
    }

    private fun navigateToBasket() {
        parentFragmentManager.commit {
            replace(R.id.layout_main_container, BasketFragment(), BasketFragment.TAG)
            addToBackStack(BasketFragment.TAG)
        }
    }

    private fun navigateToProfile() {
        parentFragmentManager.commit {
            replace(R.id.layout_main_container, OrderListFragment(), OrderListFragment.TAG)
            addToBackStack(OrderListFragment.TAG)
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    appBarViewModel.basketCount.collectLatest { result ->
                        binding.abHome.setCartCount(result)
                    }
                }

                launch {
                    appBarViewModel.isOrderingStateFlow.collect { isAllOrderSuccess ->
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
        binding.vpHome.offscreenPageLimit = 3
        TabLayoutMediator(binding.tlHome, binding.vpHome) { tab, position ->
            tab.text = adapter.homeTabTitleArray[position]
        }.attach()
    }

    companion object {
        const val TAG = "home"
    }
}