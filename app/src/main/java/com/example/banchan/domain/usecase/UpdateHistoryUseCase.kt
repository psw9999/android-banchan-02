package com.example.banchan.domain.usecase

import com.example.banchan.data.repository.HistoryRepository
import javax.inject.Inject

class UpdateHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke() = historyRepository.updateAllHistory()
}