package com.example.banchan.domain.usecase.history

import com.example.banchan.data.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryListUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    operator fun invoke() = historyRepository.getHistoryList()
}