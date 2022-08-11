package com.example.banchan.domain.usecase

import com.example.banchan.data.repository.BanChanRepository
import com.example.banchan.domain.model.ItemModel
import javax.inject.Inject

class GetSoupDishesUseCase @Inject constructor(
    private val repository: BanChanRepository
) {
    suspend operator fun invoke(): List<ItemModel> =
        repository.getSoup().getOrThrow().toSoupModel()
}