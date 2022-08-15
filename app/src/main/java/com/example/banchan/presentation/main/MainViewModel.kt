package com.example.banchan.presentation.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    var detailProductHash: String = ""
        private set
    var detailProductName: String = ""
        private set

    private val _currentFragment = MutableStateFlow(FragmentType.Home)
    val currentFragment: StateFlow<FragmentType> = _currentFragment

    fun setCurrentFragment(fragmentType: FragmentType) {
        _currentFragment.value = fragmentType
    }

    fun setDetailHash(detailHash: String){
        this.detailProductHash = detailHash
    }

    fun setProductName(productName: String){
        this.detailProductName = productName
    }

}