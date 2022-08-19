package com.example.banchan.domain.usecase.basket

import com.example.banchan.data.repository.BasketRepository
import javax.inject.Inject

class DeleteSelectedBasketItemUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(): Result<Unit> =
        basketRepository.deleteSelectedBasketItem()
}