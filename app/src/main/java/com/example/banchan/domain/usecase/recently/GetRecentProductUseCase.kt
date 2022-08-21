package com.example.banchan.domain.usecase.recently

import com.example.banchan.data.repository.RecentlyProductRepository
import com.example.banchan.domain.model.ItemModel
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
            getBasketItemUseCase().distinctUntilChanged { old, new ->
                old.getOrDefault(listOf()).size == new.getOrDefault(listOf()).size
            }
        ) { products, basketList ->
            if (products.isSuccess && basketList.isSuccess) {
                products.getOrNull()?.asFlow()?.flatMapMerge {
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
                }?.scan(emptyList<ItemModel>()) { i1, i2 -> i1 + i2 }?.onEach {
                    emit(it.sortedByDescending { it.originTime })
                }?.collect()
            }
        }.collect()
    }
}