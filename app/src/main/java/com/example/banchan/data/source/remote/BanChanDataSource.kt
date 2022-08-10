package com.example.banchan.data.source.remote

import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import com.example.banchan.domain.model.BestModel

interface BanChanDataSource {

    suspend fun getBest(): Result<List<BestModel>>

    suspend fun getMain(): Result<MainResponse>

    suspend fun getSoup(): Result<SoupResponse>

    suspend fun getSide(): Result<SideResponse>

    suspend fun getDetail(hash: String): Result<DetailResponse>
}
