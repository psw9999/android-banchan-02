package com.example.banchan.data.repository

import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import com.example.banchan.data.source.remote.BanChanRemoteDataSource
import com.example.banchan.domain.model.BestModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BanChanRepositoryImpl @Inject constructor(
    private val banChanRemoteDataSource: BanChanRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : BanChanRepository {

    override suspend fun getBest(): Result<List<BestModel>> =
        withContext(ioDispatcher) {
            return@withContext banChanRemoteDataSource.getBest()
        }

    override suspend fun getMain(): Result<MainResponse> =
        withContext(ioDispatcher) {
            return@withContext banChanRemoteDataSource.getMain()
        }

    override suspend fun getSoup(): Result<SoupResponse> =
        withContext(ioDispatcher) {
            return@withContext banChanRemoteDataSource.getSoup()
        }

    override suspend fun getSide(): Result<SideResponse> =
        withContext(ioDispatcher) {
            return@withContext banChanRemoteDataSource.getSide()
        }

    override suspend fun getDetail(hash: String): Result<DetailResponse> =
        withContext(ioDispatcher) {
            return@withContext banChanRemoteDataSource.getDetail(hash)
        }
}