package com.example.banchan.domain.usecase.home

import com.example.banchan.domain.model.BestModel
import com.example.banchan.data.repository.BanChanRepository
import javax.inject.Inject

class GetBestDishesUseCase @Inject constructor(
    private val repository: BanChanRepository
) {
    suspend operator fun invoke(): Result<List<BestModel>> =
        repository.getBest()
}