package com.example.banchan.presentation.home.adaper

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.banchan.presentation.home.best.BestFragment
import com.example.banchan.presentation.home.maincook.MainCookFragment
import com.example.banchan.presentation.home.side.SideFragment
import com.example.banchan.presentation.home.soup.SoupFragment

class HomeViewPagerAdapter(fragment : Fragment, titleArray : Array<String>) :
    FragmentStateAdapter(fragment) {

    val homeTabTitleArray = titleArray

    override fun getItemCount(): Int = homeTabTitleArray.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> BestFragment()
            1 -> MainCookFragment()
            2 -> SoupFragment()
            else -> SideFragment()
        }
    }

}