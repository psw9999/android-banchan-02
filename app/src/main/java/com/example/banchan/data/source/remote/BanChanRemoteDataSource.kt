package com.example.banchan.data.source.remote

import com.example.banchan.data.response.best.BestResponse
import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import com.example.banchan.data.api.BanChanApi
import com.example.banchan.domain.model.BestModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class BanChanRemoteDataSource @Inject constructor(
    private val service: BanChanApi,
    private val ioDispatcher: CoroutineDispatcher
) : BanChanDataSource {

    // TODO : statusCode 별로 에러메시지 다르게 보여주기
    override suspend fun getBest(): Result<List<BestModel>> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                val result = service.getBest()
                if (result.statusCode != 200) return@withContext Result.failure(Exception("서비스 오류"))
                result.body
                    .map { body ->
                        body.toBestModel()
                    }
            }
        }

    override suspend fun getMain(): Result<MainResponse> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                val result = service.getMain()
                if (result.statusCode != 200) return@withContext Result.failure(Exception("서비스 오류"))
                result
            }
        }

    override suspend fun getSoup(): Result<SoupResponse> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                val result = service.getSoup()
                if (result.statusCode != 200) return@withContext Result.failure(Exception("서비스 오류"))
                result
            }
        }

    override suspend fun getSide(): Result<SideResponse> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                val result = service.getSide()
                if (result.statusCode != 200) return@withContext Result.failure(Exception("서비스 오류"))
                result
            }
        }

    override suspend fun getDetail(hash: String): Result<DetailResponse> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                val result = service.getDetail(hash)
                result
            }
        }

}