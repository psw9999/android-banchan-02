package com.example.banchan

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.banchan.data.repository.HistoryRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrderService : LifecycleService() {
    @Inject
    lateinit var historyRepository: HistoryRepository

    override fun onCreate() {
        super.onCreate()

        lifecycleScope.launch {
            while (isActive) {
                historyRepository.updateAllHistory()
                delay(1000 * 60)
            }
        }
    }
}