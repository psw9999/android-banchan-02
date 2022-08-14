package com.example.banchan.presentation.productdetail

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.example.banchan.R
import com.example.banchan.databinding.FragmentProductDetailBinding
import com.example.banchan.presentation.base.BaseFragment
import com.example.banchan.presentation.main.MainViewModel

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun observe() {
        Log.d("DetailFragment","observe")
    }

    override fun initViews() {
        Log.d("DetailFragment","initViews")
    }

}