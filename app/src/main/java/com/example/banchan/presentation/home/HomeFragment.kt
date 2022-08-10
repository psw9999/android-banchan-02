package com.example.banchan.presentation.home

import com.example.banchan.R
import com.example.banchan.databinding.FragmentHomeBinding
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.home.adaper.HomeViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initViews() {
        initViewPager()
    }

    override fun observe() {

    }

    private fun initViewPager() {
        val adapter = HomeViewPagerAdapter(this, resources.getStringArray(R.array.home_tab_title_array))
        binding.vpHome.adapter = adapter
        TabLayoutMediator(binding.tlHome, binding.vpHome) { tab, position ->
            tab.text = adapter.homeTabTitleArray[position]
        }.attach()
    }

}