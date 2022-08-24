package com.example.banchan.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import com.example.banchan.R
import com.example.banchan.databinding.ActivityMainBinding
import com.example.banchan.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.layout_main_container, HomeFragment())
            }
        }
    }

    private fun observe() {
    }
}