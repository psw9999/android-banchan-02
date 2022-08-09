package com.example.banchan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banchan.databinding.ActivityMainBinding
import com.example.banchan.fakeData
import com.example.banchan.ui.adapter.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuAdapter = MenuAdapter()
        binding.rvBanchan.apply {
            adapter = menuAdapter
        }
        menuAdapter.submitList(fakeData)
    }
}