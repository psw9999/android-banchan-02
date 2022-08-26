package com.example.banchan.data.source.remote

import com.example.banchan.data.api.BanChanApi
import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import com.example.banchan.data.source.BanChanDataSource
import com.example.banchan.domain.model.BestModel
import javax.inject.Inject

class BanChanRemoteDataSource @Inject constructor(
    private val service: BanChanApi
) : BanChanDataSource {

    // TODO : statusCode 별로 에러메시지 다르게 보여주기
    override suspend fun getBest(): Result<List<BestModel>> =
        kotlin.runCatching {
            val result = service.getBest()
            if (result.statusCode != 200) return Result.failure(Exception("서비스 오류"))
            result.body
                .map { body ->
                    body.toBestModel()
                }
        }


    override suspend fun getMain(): Result<MainResponse> =
        kotlin.runCatching {
            val result = service.getMain()
            if (result.statusCode != 200) return Result.failure(Exception("서비스 오류"))
            result
        }


    override suspend fun getSoup(): Result<SoupResponse> =
        kotlin.runCatching {
            val result = service.getSoup()
            if (result.statusCode != 200) return Result.failure(Exception("서비스 오류"))
            result
        }


    override suspend fun getSide(): Result<SideResponse> =
        kotlin.runCatching {
            val result = service.getSide()
            if (result.statusCode != 200) return Result.failure(Exception("서비스 오류"))
            result
        }


    override suspend fun getDetail(hash: String): Result<DetailResponse> =
        kotlin.runCatching {
            val result = service.getDetail(hash)
            if (result.hash != hash) return Result.failure(Exception("서비스 오류"))
            result
        }
}