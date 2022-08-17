package com.example.banchan.domain.usecase.basket

import com.example.banchan.data.repository.BasketRepository
import com.example.banchan.data.source.local.basket.BasketItem
import javax.inject.Inject

class InsertBasketItemUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(vararg basketItem: BasketItem): Result<Unit> =
        basketRepository.insertBasketItem(*basketItem)
}