package com.example.banchan.domain.usecase

import com.example.banchan.data.repository.BanChanRepository
import com.example.banchan.data.response.detail.DetailResponse
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: BanChanRepository
) {
    suspend operator fun invoke(hash:String): Result<DetailResponse> =
        repository.getDetail(hash)
}