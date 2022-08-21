package com.example.banchan.data

import com.example.banchan.MainCoroutineRule
import com.example.banchan.data.repository.RecentlyProductRepositoryImpl
import com.example.banchan.data.source.local.recent.RecentlyProduct
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class RecentProductRepositoryImplTest {
    private lateinit var recentProductRepository: RecentlyProductRepositoryImpl
    private lateinit var recentProductLocalDataSource: RecentProductFakeDataSource

    private val newRecentProductItem1 = RecentlyProduct("test1", "test name", Date())
    private val newRecentProductItem2 = RecentlyProduct("test2", "test name", Date())

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        recentProductLocalDataSource = RecentProductFakeDataSource()
        recentProductRepository = RecentlyProductRepositoryImpl(recentProductLocalDataSource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveRecentProduct_saveRecentProductToLocal() = runTest {
        val originData = mutableListOf<Result<List<RecentlyProduct>>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            recentProductLocalDataSource.getRecentlyProducts().toList(originData)
        }

        recentProductRepository.insertRecentlyProduct(newRecentProductItem1)
        assertEquals(originData[0].getOrNull()?.contains(newRecentProductItem1), true)
        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveRecentProduct_saveRecentProductToLocal_error() = runTest {
        val originData = mutableListOf<Result<List<RecentlyProduct>>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            recentProductLocalDataSource.getRecentlyProducts().toList(originData)
        }

        recentProductRepository.insertRecentlyProduct(newRecentProductItem1)
        assertEquals(originData[0].getOrNull()?.contains(newRecentProductItem2), true)
        collectJob.cancel()
    }
}