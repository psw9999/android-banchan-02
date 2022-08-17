package com.example.banchan.domain.usecase.recently

import com.example.banchan.data.repository.RecentlyProductRepository
import com.example.banchan.data.source.local.recent.RecentlyProduct
import javax.inject.Inject

class InsertRecentlyItemUseCase @Inject constructor(
    private val recentlyRepository: RecentlyProductRepository
) {
    suspend operator fun invoke(vararg recentlyProduct: RecentlyProduct): Result<Unit> =
        recentlyRepository.insertRecentlyProduct(*recentlyProduct)
}