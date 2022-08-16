package com.example.banchan.data.repository

import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import com.example.banchan.data.source.BanChanDataSource
import com.example.banchan.domain.model.BestModel
import javax.inject.Inject

class BanChanRepositoryImpl @Inject constructor(
    private val banChanRemoteDataSource: BanChanDataSource
) : BanChanRepository {

    override suspend fun getBest(): Result<List<BestModel>> =
        banChanRemoteDataSource.getBest()


    override suspend fun getMain(): Result<MainResponse> =
        banChanRemoteDataSource.getMain()


    override suspend fun getSoup(): Result<SoupResponse> =
        banChanRemoteDataSource.getSoup()


    override suspend fun getSide(): Result<SideResponse> =
        banChanRemoteDataSource.getSide()


    override suspend fun getDetail(hash: String): Result<DetailResponse> =
        banChanRemoteDataSource.getDetail(hash)

}