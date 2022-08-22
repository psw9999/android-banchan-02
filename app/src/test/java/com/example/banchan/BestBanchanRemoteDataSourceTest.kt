package com.example.banchan

import com.example.banchan.data.api.BanChanApi
import com.example.banchan.data.response.best.BestResponse
import com.example.banchan.data.response.detail.DetailResponse
import com.example.banchan.data.response.main.MainResponse
import com.example.banchan.data.response.side.SideResponse
import com.example.banchan.data.response.soup.SoupResponse
import com.google.common.truth.Truth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.io.File
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class BestBanchanRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var client: OkHttpClient
    private lateinit var banchanApi: BanChanApi

    private fun readFile(fileName: String): String {
        return File("src/test/java/com/example/banchan/resources/$fileName").readText()
    }

    @Before
    fun initialize() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        client = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build()

        banchanApi = Retrofit.Builder()
            .client(client)
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
            .create(BanChanApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `기획전의 응답 성공 데이터 가져오기`() = runTest {
        //given
        val successResponseJson = readFile("best-banchan-200.json")
        val response = MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(successResponseJson)
        }
        mockWebServer.enqueue(response)

        // when
        val actualResult = banchanApi.getBest()
        val expected = Json.decodeFromString<BestResponse>(successResponseJson)

        // then
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

    @Test
    fun `메인요리의 응답 성공 데이터 가져오기`() = runTest {
        //given
        val successResponseJson = readFile("main-banchan-200.json")
        val response = MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(successResponseJson)
        }
        mockWebServer.enqueue(response)

        // when
        val actualResult = banchanApi.getMain()
        val expected = Json.decodeFromString<MainResponse>(successResponseJson)

        // then
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

    @Test
    fun `국물요리 응답 성공 데이터 가져오기`() = runTest {
        //given
        val successResponseJson = readFile("soup-banchan-200.json")
        val response = MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(successResponseJson)
        }
        mockWebServer.enqueue(response)

        // when
        val actualResult = banchanApi.getSoup()
        val expected = Json.decodeFromString<SoupResponse>(successResponseJson)

        // then
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

    @Test
    fun `사이드요리 응답 성공 데이터 가져오기`() = runTest {
        //given
        val successResponseJson = readFile("side-banchan-200.json")
        val response = MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(successResponseJson)
        }
        mockWebServer.enqueue(response)

        // when
        val actualResult = banchanApi.getSide()
        val expected = Json.decodeFromString<SideResponse>(successResponseJson)

        // then
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

    @Test
    fun `요리 상세정보 응답 성공 데이터 가져오기`() = runTest {
        //given
        val successResponseJson = readFile("detail-banchan-200.json")
        val response = MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(successResponseJson)
        }
        mockWebServer.enqueue(response)

        // when
        val actualResult = banchanApi.getDetail("H602F")
        val expected = Json.decodeFromString<DetailResponse>(successResponseJson)

        // then
        Truth.assertThat(actualResult).isEqualTo(expected)
    }

}