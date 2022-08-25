package com.example.banchan.domain.usecase.recently

import androidx.paging.PagingData
import androidx.paging.map
import com.example.banchan.data.repository.RecentlyProductRepository
import com.example.banchan.domain.model.ItemModel
import com.example.banchan.domain.usecase.basket.GetBasketItemUseCase
import com.example.banchan.domain.usecase.detail.GetProductDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentProductPagingUseCase @Inject constructor(
    private val getBasketItemUseCase: GetBasketItemUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val recentlyProductRepository: RecentlyProductRepository
) {
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<PagingData<Result<ItemModel>>> =
        getBasketItemUseCase().flatMapLatest { basketList ->
            recentlyProductRepository.getRecentlyProductPagingData().map {
                it.map { product -> getProductDetailUseCase(product.hash).mapCatching { response ->
                    response.toItemModel(
                        name = product.name,
                        isCartAdded = basketList.getOrDefault(listOf()).map { it.hash }
                            .contains(product.hash),
                        originTime = product.recentlyTime.time
                    )
                }}
            }
        }
}