package com.example.banchan.data.api

import com.example.banchan.data.response.best.BestResponse
import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BanChanApi {

    @GET("onban/best")
    suspend fun getBest(): BestResponse

    @GET("onban/main")
    suspend fun getMain(): MainResponse

    @GET("onban/soup")
    suspend fun getSoup(): SoupResponse

    @GET("onban/side")
    suspend fun getSide(): SideResponse

    @GET("onban/detail/{detail_hash}")
    suspend fun getDetail(@Path("detail_hash") hash: String): DetailResponse
}