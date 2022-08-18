package com.example.banchan.domain.usecase.basket

import com.example.banchan.data.repository.BasketRepository
import javax.inject.Inject

class UpdateAllBasketIsSelectedUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(isSelected: Int): Result<Unit> =
        basketRepository.updateAllBasketIsSelected(isSelected)
}