package com.example.banchan.presentation.recentlyproduct

import androidx.lifecycle.ViewModel
import com.example.banchan.domain.usecase.recently.GetRecentProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentlyProductViewModel @Inject constructor(
    private val getRecentProductUseCase: GetRecentProductUseCase
) : ViewModel() {
    val items = getRecentProductUseCase()
}