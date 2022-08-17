package com.example.banchan.domain.usecase.basket

import com.example.banchan.data.repository.BasketRepository
import javax.inject.Inject

class GetBasketItemUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    operator fun invoke() =
        basketRepository.getBasketItems()
}