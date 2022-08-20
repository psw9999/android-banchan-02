package com.example.banchan.domain.usecase.history

import com.example.banchan.data.repository.HistoryRepository
import com.example.banchan.data.source.local.history.HistoryItem
import javax.inject.Inject

class InsertHistoryItemsUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(items: List<HistoryItem>, deliveryFee: Int) =
        historyRepository.insertHistoryWithItems(items, deliveryFee)
}