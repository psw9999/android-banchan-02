package com.example.banchan.domain.usecase.recently

import com.example.banchan.data.repository.RecentlyProductRepository
import javax.inject.Inject

class GetRecentlyItemUseCase @Inject constructor(
    private val recentlyRepository: RecentlyProductRepository
) {
    operator fun invoke() =
        recentlyRepository.getRecentlyProducts()
}