package com.example.banchan.domain.usecase.recently

import com.example.banchan.data.repository.RecentlyProductRepository
import com.example.banchan.data.source.local.recent.RecentlyProduct
import java.util.*
import javax.inject.Inject

class SaveRecentProduct @Inject constructor(
    private val recentlyProductRepository: RecentlyProductRepository
) {
    suspend operator fun invoke(hash: String, name: String) =
        recentlyProductRepository.insertRecentlyProduct(
            RecentlyProduct(
                hash = hash,
                name = name,
                recentlyTime = Date()
            )
        )
}