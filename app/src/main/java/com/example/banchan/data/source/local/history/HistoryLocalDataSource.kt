package com.example.banchan.data.source.local.history

import com.example.banchan.data.source.HistoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryLocalDataSource @Inject constructor(
    private val historyDao: HistoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HistoryDataSource {
    override fun getHistoryWithItems() = historyDao.getHistoryWithItems()


    override suspend fun insertHistoryWithItems(items: List<HistoryItem>) {
        withContext(ioDispatcher) {
            historyDao.insertHistoryWithItems(items)
        }
    }
}