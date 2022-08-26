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
    private val refresh = MutableSharedFlow<Boolean>(replay = 1)

    @OptIn(FlowPreview::class)
    operator fun invoke() = flow {
        combine(
            refresh,
            recentlyProductRepository.getRecentlyProducts(),
            getBasketItemUseCase()
        ) { _, products, basketList ->
            if (products.isSuccess && basketList.isSuccess) {
                runCatching {
                    val result = products.getOrNull()?.asFlow()?.flatMapMerge {
                        flow {
                            val response = getProductDetailUseCase(it.hash).getOrThrow()
                            emit(
                                response.toItemModel(
                                    it.name,
                                    it.hash in basketList.getOrDefault(listOf()).map { it.hash },
                                    it.recentlyTime.time
                                )
                            )
                        }
                    }?.toList()?.sortedByDescending { it.originTime }
                    emit(result)
                }.onFailure {
                    emit(null)
                }
            }
        }.collect()
    }

    suspend fun refresh() {
        refresh.emit(true)
    }
}