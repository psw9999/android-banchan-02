package com.example.banchan.presentation.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.FragmentHomeBinding
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.adapter.home.HomeViewPagerAdapter
import com.example.banchan.presentation.main.BasketViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val basketViewModel: BasketViewModel by activityViewModels()

    override fun initViews() {
        initViewPager()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                basketViewModel.basketList.collectLatest {
                    binding.abHome.setCartCount(it.size)
                }
            }
        }
    }

    private fun initViewPager() {
        val adapter = HomeViewPagerAdapter(this, resources.getStringArray(R.array.home_tab_title_array))
        binding.vpHome.adapter = adapter
        TabLayoutMediator(binding.tlHome, binding.vpHome) { tab, position ->
            tab.text = adapter.homeTabTitleArray[position]
        }.attach()
    }

}