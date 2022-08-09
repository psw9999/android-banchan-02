package com.example.banchan.di

import com.example.banchan.remote.BanChanApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient()
}

@Module
@InstallIn(SingletonComponent::class)
object ConverterModule {
    @Provides
    @Singleton
    fun provideJsonConverter() = Json.asConverterFactory(MediaType.parse("application/json")!!)
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.codesquad.kr/"

    @Provides
    @Singleton
    fun provideBanChanApi(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): BanChanApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converter)
            .baseUrl(BASE_URL)
            .build()
            .create(BanChanApi::class.java)
    }
}