package com.example.banchan.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.ActivityMainBinding
import com.example.banchan.presentation.basket.BasketFragment
import com.example.banchan.presentation.home.HomeFragment
import com.example.banchan.presentation.orderlist.OrderListFragment
import com.example.banchan.presentation.ordersuccess.OrderSuccessFragment
import com.example.banchan.presentation.productdetail.ProductDetailFragment
import com.example.banchan.presentation.recentlyproduct.RecentlyProductFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
    }

    private fun observe() {
        this.lifecycleScope.launch {
            this@MainActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentFragment.collectLatest {
                    val targetFragment = supportFragmentManager.findFragmentByTag(it.tag)
                    if (targetFragment == null) {
                        val fragment = when (it) {
                            FragmentType.Home -> HomeFragment()
                            FragmentType.Basket -> BasketFragment()
                            FragmentType.OrderDetail -> HomeFragment()
                            FragmentType.ProductDetail -> ProductDetailFragment()
                            FragmentType.RecentlyViewedProduct -> RecentlyProductFragment()
                            FragmentType.OrderList -> OrderListFragment()
                        }
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            if (it != FragmentType.Home) addToBackStack(it.tag)
                            replace(R.id.layout_main_container, fragment, it.tag)
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when (supportFragmentManager.findFragmentById(R.id.layout_main_container)?.tag) {
            FragmentType.Home.tag -> mainViewModel.setCurrentFragment(FragmentType.Home)
            FragmentType.Basket.tag -> mainViewModel.setCurrentFragment(FragmentType.Basket)
            FragmentType.OrderDetail.tag -> mainViewModel.setCurrentFragment(FragmentType.OrderDetail)
            FragmentType.ProductDetail.tag -> mainViewModel.setCurrentFragment(FragmentType.ProductDetail)
            FragmentType.RecentlyViewedProduct.tag -> mainViewModel.setCurrentFragment(FragmentType.RecentlyViewedProduct)
            FragmentType.OrderList.tag -> mainViewModel.setCurrentFragment(FragmentType.OrderList)
        }
    }
}