package com.example.banchan.domain.usecase.history

import com.example.banchan.data.repository.HistoryRepository
import javax.inject.Inject

class UpdateHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(id: Long) = historyRepository.updateHistory(id)
}