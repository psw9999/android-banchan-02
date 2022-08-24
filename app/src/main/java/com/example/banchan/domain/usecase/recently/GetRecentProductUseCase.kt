package com.example.banchan.domain.usecase.recently

import com.example.banchan.data.repository.RecentlyProductRepository
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetRecentProductUseCase @Inject constructor(
    private val getBasketItemUseCase: GetBasketItemUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val recentlyProductRepository: RecentlyProductRepository
) {
    @OptIn(FlowPreview::class)
    operator fun invoke() = flow {
        combine(
            recentlyProductRepository.getRecentlyProducts(),
            getBasketItemUseCase()
        ) { products, basketList ->
            if (products.isSuccess && basketList.isSuccess) {
                val result = products.getOrNull()?.asFlow()?.flatMapMerge {
                    flow {
                        getProductDetailUseCase(it.hash).getOrNull()?.let { response ->
                            emit(
                                response.toItemModel(
                                    it.name,
                                    it.hash in basketList.getOrDefault(listOf()).map { it.hash },
                                    it.recentlyTime.time
                                )
                            )
                        }
                    }
                }?.toList()?.sortedByDescending { it.originTime }
                emit(result)
            }
        }.collect()
    }
}