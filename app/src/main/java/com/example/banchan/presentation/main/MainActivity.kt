package com.example.banchan.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.R
import com.example.banchan.databinding.ActivityMainBinding
import com.example.banchan.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
    }

    private fun observe() {
        this.lifecycleScope.launch {
            this@MainActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentFragment.collectLatest {
                    // 추후 다른 Fragment 생성 후 수정 예정
                    val fragment = when(it) {
                        FragmentType.Home -> HomeFragment()
                        FragmentType.Cart -> HomeFragment()
                        FragmentType.OrderDetail -> HomeFragment()
                        FragmentType.ProductDetail -> HomeFragment()
                        FragmentType.RecentlyViewedProduct -> HomeFragment()
                    }
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        if(it != FragmentType.Home) addToBackStack(it.tag)
                        replace(R.id.layout_main_container, fragment, it.tag)
                    }
                }
            }
        }
    }

}